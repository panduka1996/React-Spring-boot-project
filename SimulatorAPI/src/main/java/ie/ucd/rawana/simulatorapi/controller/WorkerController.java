package ie.ucd.rawana.simulatorapi.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.util.mxCellRenderer;
import ie.ucd.rawana.simulatorapi.DTO.*;
import ie.ucd.rawana.simulatorapi.model.Location;
import ie.ucd.rawana.simulatorapi.model.Worker;
import ie.ucd.rawana.simulatorapi.services.MapService;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import ie.ucd.rawana.simulatorapi.services.ProductService;
import ie.ucd.rawana.simulatorapi.services.WorkerService;

import javax.imageio.ImageIO;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class WorkerController {

    @Autowired
    private MapService mapService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ProductService productService;

    Graph<String, DefaultEdge> directedGraph =
            new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

    private RestTemplate restTamplate = new RestTemplate();

    private String baseUrl = "http://localhost:8084/";

    private String baseUrl1 = "http://localhost:8083/";

    private int imageNo = 1;

    private int step = 0;

    private int init = 0;

    private String aisles = "";

    private String section = "";

    private String shelf = "";

    private String[] packingStation = new String[2];

    private Map<String, List<String>> stacks = new HashMap<String, List<String>>();

    private Map<String, List<String>> paths = new HashMap<String, List<String>>();

    private Map<String, WorkerDetail> workerDel = new HashMap<String, WorkerDetail>();

    private Map<String, Set<ItemDto>> items = new HashMap<String, Set<ItemDto>>();

    private Map<String, WeightDto> totalWeights = new HashMap<String, WeightDto>();

    private Map<String, DestinationDto> desLocations = new HashMap<String, DestinationDto>();

    private Map<String, SourceDto> souLocations = new HashMap<String, SourceDto>();

    ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths;

    private WorkerSetDto workerSet = new WorkerSetDto();

    private Set<ItemDto> orderItems = new HashSet<ItemDto>();

    public void setWorkerToMap(){

        List<String> workerIds = workerService.getWorkerIds();

        for(String workerId : workerIds){

            stacks.put(workerId,new ArrayList<>());
            paths.put(workerId,new ArrayList<>());
            workerDel.put(workerId,new WorkerDetail());
            items.put(workerId,new HashSet<ItemDto>());
            totalWeights.put(workerId,new WeightDto(0));
            desLocations.put(workerId,new DestinationDto("a1.0"));
            souLocations.put(workerId,new SourceDto("a1.0"));
        }

    }


    public void setWorkerDetails(String WorkerName){

        Worker worker = workerService.getWorkerById(WorkerName);

        DestinationDto desLocation = desLocations.get(WorkerName);

        WorkerDto workerdto = new WorkerDto();

        workerdto.setName(worker.getEmployeeName());
        workerdto.setCapacity(worker.getCapacity());
        workerdto.setLocation(desLocation.getDesLocation());

        workerSet.getWorkers().add(workerdto);

    }

    public void setItem(String orderId,String WorkerName){

        String url = baseUrl1 + "getOrderItemsWithWeight/" + Long.parseLong(orderId);

        ResponseEntity<ItemWithWeight[]> response =
                restTamplate.getForEntity(url, ItemWithWeight[].class);

        ItemWithWeight[] itemIds = response.getBody();

        Set<ItemDto> item = items.get(WorkerName);

        item.clear();

        float totalWeight = 0;

        for(ItemWithWeight itemid : itemIds){

            String weight = productService.getItemWeight(Long.parseLong(itemid.getItemId()));

            totalWeight = totalWeight + Float.parseFloat(weight) * Integer.parseInt(itemid.getCount());

            ItemDto itemdto = new ItemDto();

            itemdto.setId(Long.parseLong(itemid.getItemId()));
            itemdto.setWeight(weight);

            item.add(itemdto);

            orderItems.add(itemdto);
        }


        WeightDto weightdto = totalWeights.get(WorkerName);
        weightdto.setWeight(totalWeight);

    }


    @GetMapping("/workers")
    public List<String> getWorkers() {

        List<String> workerIds = workerService.getWorkerIdsForAuths();

        return workerIds;
    }

    @GetMapping("/getPickers")
    public List<String> getPickers() {

        List<String> workerIds = workerService.getWorkerIds();

        return workerIds;
    }


    @GetMapping("/AuthonticateWorker/{username}")
    public AuthDto AuthonticateWorker(@PathVariable(value = "username") String username) {


        boolean validate = false;

        String worker = "";

        List<String> workerIds = workerService.getWorkerIdsForAuths();

        for(String workerId : workerIds) {

            if(username.equals(workerId)) {

                Worker wor = workerService.getWorkerById(workerId);
                worker = wor.getJobType();
                validate = true;
            }

        }

        AuthDto auth = new AuthDto();
        auth.setValid(validate);
        auth.setJobType(worker);

        return auth;

    }

    @PatchMapping("/workers/{name}")
    public void updateUri(@RequestBody NotifyUri model,@PathVariable(value = "name") String WorkerName) {

        if(init == 0){
            setWorkerToMap();
            init = 1;
        }

        WorkerDetail wdetail = workerDel.get(WorkerName);
        wdetail.setNotificationUri(model.getNotificationUri());

        String url = model.getNotificationUri();

        ResponseEntity<Boolean> response =
                restTamplate.getForEntity(url,Boolean.class);

        Boolean validate = response.getBody();

    }


    @GetMapping("/workers/{name}")
    public WorkerDetail getEmployeeOrderDetails(@PathVariable(value = "name") String WorkerName) {

        if(init == 0){
            setWorkerToMap();
            init = 1;
        }

        Worker worker = workerService.getWorkerById(WorkerName);

        Set<ActionDto> acts = new HashSet<>();

        String lastAction = "";

        List<String> stack = stacks.get(WorkerName);

        if(stack.size() > 0){

            String[] actions = new String[stack.size()];

            for(int i=0;i<stack.size();i++){

                actions[i] = stack.get(i);

                ActionDto action = new ActionDto();
                action.setAction(actions[i]);

                acts.add(action);
            }

            lastAction = actions[actions.length-1];

        }

        Set<ItemDto> item = items.get(WorkerName);
        WeightDto totalWeight = totalWeights.get(WorkerName);
        DestinationDto desLocation = desLocations.get(WorkerName);

        WorkerDetail wdetail = workerDel.get(WorkerName);
        wdetail.setName(worker.getEmployeeName());
        wdetail.setCapacity(worker.getCapacity());
        wdetail.setLocation(desLocation.getDesLocation());
        wdetail.setActions(acts);
        wdetail.setWeight(totalWeight.getWeight());
        wdetail.setHolding(item);

        if(lastAction.equals("pack")){
            wdetail.setNextAction(null);
        }

        return wdetail;

    }

    @GetMapping("/getWorkerWeight/{workerId}")
    public String getWorkerWeight(@PathVariable(value = "workerId") String workerName) {

        Worker worker = workerService.getWorkerById(workerName);

        String workerWeight = worker.getCapacity();

        return workerWeight;

    }

    @PutMapping("/workers/{name}/nextAction")
    public AcceptAction map(@RequestBody WorkerAction model,@PathVariable(value = "name") String workerName) throws IOException {

        if(init == 0){
            setWorkerToMap();
            init = 1;
        }

        setWorkerDetails(workerName);

        String type = model.getType();

        if(type.equals("move")){

            String desLocation = model.getArguments();

            WorkerDetail wdetail = workerDel.get(workerName);
            wdetail.setNextAction(type);

            SourceDto source = souLocations.get(workerName);
            String souLocation = source.getSouLocation();

            setMapFlor();

            StrongConnectivityAlgorithm<String, DefaultEdge> scAlg =
                    new KosarajuStrongConnectivityInspector<>(directedGraph);

            List<Graph<String, DefaultEdge>> stronglyConnectedSubgraphs =
                    scAlg.getStronglyConnectedComponents();

            for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
                //System.out.println(stronglyConnectedSubgraphs.get(i));
            }


            System.out.println("Shortest path from " + souLocation + " to " + desLocation);

            DijkstraShortestPath<String, DefaultEdge> dijkstraAlg =
                    new DijkstraShortestPath<>(directedGraph);

            iPaths = dijkstraAlg.getPaths(souLocation);

            System.out.println(iPaths.getPath(desLocation) + "\n");

            /////////////////////////////////////////////////////////////////////////

            Graph<String, DefaultEdge> directedGraphnew =
                    new DefaultDirectedGraph<   String, DefaultEdge>(DefaultEdge.class);

            for(int i=0;i<iPaths.getPath(desLocation).getVertexList().size();i++){

                directedGraphnew.addVertex(iPaths.getPath(desLocation).getVertexList().get(i));
            }

            for(int i=0;i<iPaths.getPath(desLocation).getEdgeList().size();i++){

                String path = iPaths.getPath(desLocation).getEdgeList().get(i).toString();

                String pathArray[] = path.split("[:]");

                String firstPart = pathArray[0].substring(1);

                String lastPart = pathArray[1].substring(0, pathArray[1].length() - 1);

                directedGraphnew.addEdge(firstPart.trim(),lastPart.trim());
            }

            JGraphXAdapter<String, DefaultEdge> graphAdapter;
            graphAdapter = new JGraphXAdapter<String, DefaultEdge>(directedGraphnew);

            mxIGraphLayout layout = new mxOrganicLayout(graphAdapter);
            layout.execute(graphAdapter.getDefaultParent());

            BufferedImage image =
                    mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);

            try{
                File imgFile = new File("..//paper-dashboard//src//views//images//diagrams//step" + imageNo + ".png");
                ImageIO.write(image, "PNG", imgFile);
            }
            catch (Exception e){
                System.out.println("stored");
            }

            imageNo = imageNo + 1;

            ////////////////////////////////////////////////////////////////////////

            List<String> path = paths.get(workerName);
            path.add(String.valueOf(iPaths.getPath(desLocation)));

            SourceDto source1 = souLocations.get(workerName);
            source1.setSouLocation(desLocation);

            List<String> stack = stacks.get(workerName);
            stack.add(type);

            wdetail.setNextAction(null);

            DestinationDto destination = desLocations.get(workerName);
            destination.setDesLocation(desLocation);

            AcceptAction accept = new AcceptAction();

            accept.setType(type);
            accept.setArguments(desLocation);
            accept.setSuccess(true);
            accept.setStep(step);

            return accept;

        }

        else if(type.equals("pick")){

            shelf = model.getArguments();

            WorkerDetail wdetail = workerDel.get(workerName);
            wdetail.setNextAction(type);

            List<String> stack = stacks.get(workerName);
            stack.add(type);

            wdetail.setNextAction(null);

            AcceptAction accept = new AcceptAction();

            accept.setType(type);
            accept.setArguments(shelf);
            accept.setSuccess(true);
            accept.setStep(step);

            return accept;

        }

        else if(type.equals("drop")){

            String dropLocation = model.getArguments();

            WorkerDetail wdetail = workerDel.get(workerName);
            wdetail.setNextAction(type);

            List<String> stack = stacks.get(workerName);
            stack.add(type);

            wdetail.setNextAction(null);

            DestinationDto destination = desLocations.get(workerName);
            destination.setDesLocation(dropLocation);

            AcceptAction accept = new AcceptAction();

            accept.setType(type);
            accept.setArguments(dropLocation);
            accept.setSuccess(true);
            accept.setStep(step);

            return accept;

        }

        else if(type.equals("pack")){

            String order = model.getArguments();

            setItem(order,workerName);

            WorkerDetail wdetail = workerDel.get(workerName);
            wdetail.setNextAction(type);

            List<String> stack = stacks.get(workerName);
            stack.add(type);

            wdetail.setNextAction(null);

            AcceptAction accept = new AcceptAction();

            accept.setType(type);
            accept.setArguments(order);
            accept.setSuccess(true);
            accept.setStep(step);

            return accept;

        }
        else{

            AcceptAction accept = new AcceptAction();

            accept.setType(type);
            accept.setArguments(model.getArguments());
            accept.setSuccess(false);
            accept.setStep(step);

            return accept;

        }



    }

    @GetMapping("/getPath/{workerName}")
    public List<String> getPath(@PathVariable(value = "workerName") String workerName){

        if(init == 0){
            setWorkerToMap();
            init = 1;
        }

        List<String> path = paths.get(workerName);

        return path;
    }

    public void setMapFlor(){

        String[] locations = mapService.getLocation();

        for(int i=0;i<locations.length;i++){

            directedGraph.addVertex(locations[i]);
        }

        for(int i=0;i<locations.length;i++){

            if(i == locations.length - 1){
                break;
            }

            directedGraph.addEdge(locations[i], locations[i+1]);

        }

        for(int i=locations.length-1;i>=0;i--){

            if(i == 0){
                break;
            }

            directedGraph.addEdge(locations[i], locations[i-1]);

        }

        directedGraph.addEdge(locations[0],locations[locations.length-1]);
        directedGraph.addEdge(locations[locations.length-1], locations[0]);

    }


    @GetMapping("/map")
    public MapDto getmap(){

        setMapFlor();

        Set<String> vertices = directedGraph.vertexSet();

        String[] locations = new String[vertices.size()];

        int index = 0;

        for(String i : vertices){

            locations[index] = i;

            index++;

        }

        List<String> edges = new ArrayList<String>();

        for(int i=0;i<locations.length;i++){

            if(i == locations.length - 1){
                break;
            }

            edges.add(String.valueOf(directedGraph.getEdge(locations[i],locations[i+1])));

        }

        for(int i=locations.length-1;i>=0;i--){

            if(i == 0){
                break;
            }

            edges.add(String.valueOf(directedGraph.getEdge(locations[i],locations[i-1])));

        }

        edges.add(String.valueOf(directedGraph.getEdge(locations[0],locations[locations.length-1])));
        edges.add(String.valueOf(directedGraph.getEdge(locations[locations.length-1],locations[0])));

        MapDto map = new MapDto();

        map.setVertices(vertices);
        map.setEdges(edges);

        return map;

    }

    @GetMapping("/map/vertices")
    public Set<String> getMapVertices(){

        setMapFlor();

        Set<String> vertices = directedGraph.vertexSet();

        return vertices;
    }

    @GetMapping("/map/vertices/{id}")
    public VerticeDetailDto getMapVerticesDetails(@PathVariable(value = "id") long id){

        Location location = mapService.getVerticeDetails(id);

        VerticeDetailDto detail = new VerticeDetailDto();

        detail.setId(location.getId());
        detail.setType(location.getLocation());

        return detail;
    }

    @GetMapping("/map/vertices/{id}/opposite")
    public List<String> getVerticesOpposite(@PathVariable(value = "id") long id){

        long preVertex;
        long postVertex;

        List<String> opposites = new ArrayList<String>();

        String[] locations = mapService.getLocation();

        if(id == 1){
            preVertex = locations.length;
            postVertex = id + 1;
        }
        else if(id == locations.length){
            preVertex = id - 1;
            postVertex = 1;
        }
        else{
            preVertex = id - 1;
            postVertex = id + 1;
        }


        Location prelocation = mapService.getVerticeDetails(preVertex);

        Location postlocation = mapService.getVerticeDetails(postVertex);

        opposites.add(prelocation.getLocation());
        opposites.add(postlocation.getLocation());

        return opposites;
    }

    @PutMapping("/step")
    public void putStep(@RequestBody StepDto model){

        step = model.getStep();

        System.out.println(step);
    }

    @GetMapping("/step")
    public StepDto getStep(){

        StepDto stepdto = new StepDto();

        stepdto.setStep(step);

        return stepdto;

    }

    @PutMapping("/configuration")
    public void putConfiguration(@RequestBody ResetConfig model){

        aisles = model.getAisles();
        section = model.getSections();
        shelf = model.getShelves();
        packingStation = model.getPackingAreas();
        workerSet.setWorkers(model.getWorkers());
        orderItems = model.getItems();

        workerDel.clear();
        stacks.clear();
        items.clear();
        desLocations.clear();
        souLocations.clear();
        totalWeights.clear();
        paths.clear();
        imageNo = 1;
        init = 0;

    }

    @GetMapping("/configuration")
    public StepDto getConfiguration(){

        StepDto stepdto = new StepDto();

        stepdto.setStep(step);

        return stepdto;

    }

    @GetMapping("/getCloseItem/{itemSou}/{itemLoc}")
    public String getCloseItem(@PathVariable(value = "itemSou") String itemSou,@PathVariable(value = "itemLoc") String itemLoc){

        Graph<String, DefaultEdge> directedGraphNew =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPathsNew;

        ///////////////////////////////////////

            String[] locations = mapService.getLocation();

            for(int i=0;i<locations.length;i++){

                directedGraphNew.addVertex(locations[i]);
            }

            for(int i=0;i<locations.length;i++){

                if(i == locations.length - 1){
                    break;
                }

                directedGraphNew.addEdge(locations[i], locations[i+1]);

            }

            for(int i=locations.length-1;i>=0;i--){

                if(i == 0){
                    break;
                }

                directedGraphNew.addEdge(locations[i], locations[i-1]);

            }

        directedGraphNew.addEdge(locations[0],locations[locations.length-1]);
        directedGraphNew.addEdge(locations[locations.length-1], locations[0]);


        //////////////////////////////////////

        StrongConnectivityAlgorithm<String, DefaultEdge> scAlgNew =
                new KosarajuStrongConnectivityInspector<>(directedGraphNew);

        List<Graph<String, DefaultEdge>> stronglyConnectedSubgraphsNew =
                scAlgNew.getStronglyConnectedComponents();

        DijkstraShortestPath<String, DefaultEdge> dijkstraAlgNew =
                new DijkstraShortestPath<>(directedGraphNew);

        iPathsNew = dijkstraAlgNew.getPaths(itemSou);

        String path = String.valueOf(iPathsNew.getPath(itemLoc));

        return path;
    }

    //DonPlex
    @GetMapping("/getEmployees")
    public Set<WorkerDto> getEmployees() {

        Set<WorkerDto> setEmpDto = new HashSet<>();
        List<Worker> worker = workerService.getAllEmployees();

        for (Worker work : worker) {

            WorkerDto workDto = new WorkerDto();

            workDto.setId(work.getId());
            workDto.setName(work.getEmployeeName());
            workDto.setJob_type(work.getJobType());
            workDto.setCapacity(work.getCapacity());

            setEmpDto.add(workDto);
        }
        return setEmpDto;
    }

    @DeleteMapping("/removeEmployee/{id}")
    public void deleteEmployee(@PathVariable long id) {
        workerService.deleteEmployee(id);
    }

    @PostMapping("/saveEmployee")
    public boolean saveEmployee(@RequestBody WorkerDto model) {

        boolean feedBack = false;
        Worker work = new Worker();

        work.setEmployeeName(model.getName());
        work.setJobType(model.getJob_type());
        work.setCapacity(model.getCapacity());

        try {
            workerService.saveEmployee(work);
            feedBack = true;

        } catch (Exception e) {
            feedBack = false;
        }
        return feedBack;
    }


}
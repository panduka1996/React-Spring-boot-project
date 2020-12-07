
// import Dashboard from "views/Dashboard.jsx";
// import Typography from "views/Typography.jsx";
// import TableList from "views/Tables.jsx";
import Orders from "views/order/Order.jsx"
//import DailyOrders from "views/supervisor/dailyOrders.jsx"
import AvailableOrders from "views/picker/availableOrders.jsx"
import AvailablePackerOrders from "views/packer/availablePackerOrders.jsx" 
import AvailableShipperOrders from "views/shipper/availableShipperOrders.jsx" 
// import WorkerAccuracy from "views/supervisor/pickerAccuracy.jsx" 
import NewOrders from "views/supervisor/newOrders.jsx" 
import WorkerProgress from "views/workersProgress/workersProgress";
// import Icons from "views/Icons";
import DailyOrders from "views/dailyOrders/dailyOrders";
// import CollapsibleTable from "views/pickerStage/pickerStage";
import ClockMain from "views/clock/ClockMain.jsx";
import ProdctMain from "views/products/productMain";
import EmployeeMain from "views/employees/employeeMain";
import MapDisplay from "views/map/map";
import PickerAction from "views/pickerAction/pickers.jsx"

var routes = [
  // {
  //   path: "/dashboard",
  //   name: "Dashboard",
  //   icon: "nc-icon nc-bank",
  //   component: Dashboard,
  //   layout: "/admin"
  // },
  {
    path: "/clock",
    name: "Clock",
    icon: "nc-icon nc-time-alarm",
    component: ClockMain,
    layout: "/admin"
  },
  {
    path: "/store",
    name: "Store",
    icon: "nc-icon nc-shop",
    component: Orders,
    layout: "/admin"
  },
  {
    path: "/dailyorders",
    name: "Daily Orders",
    icon: "nc-icon nc-bag-16",
    component: DailyOrders,
    layout: "/admin"
  },
  {
    path: "/workersprogress",
    name: "Workers Progress",
    icon: "nc-icon nc-sound-wave",
    component: WorkerProgress,
    layout: "/admin"
  },
  // {
  // path: "/dailyorder",
  // name: "daily orders",
  // icon: "nc-icon nc-diamond",
  // component: dailyorders,
  // layout: "/admin"
  //  },
  // {
  //   path: "/WorkerAccuracy",
  //   name: "Worker Accuracy",
  //   icon: "nc-icon nc-diamond",
  //   component: WorkerAccuracy,
  //   layout: "/admin"
  // },
  {
    path: "/orderAssign",
    name: "Orders Assign",
    icon: "nc-icon nc-bulb-63",
    component: NewOrders,
    layout: "/admin"
  },
  {
    path: "/pickerAction",
    name: "Picker Actions",
    icon: "nc-icon nc-vector",
    component: PickerAction,
    layout: "/admin"
  },
  {
    path: "/mapdisplay",
    name: "DC Map",
    icon: "nc-icon nc-world-2",
    component: MapDisplay,
    layout: "/admin"
  },
  {
    path: "/availableOrder",
    name: "Orders for picker",
    icon: "nc-icon nc-tap-01",
    component: AvailableOrders,
    layout: "/admin"
  },
  {
    path: "/availablePackerOrder",
    name: "Orders for packer",
    icon: "nc-icon nc-box-2",
    component: AvailablePackerOrders,
    layout: "/admin"
  },
  {
    path: "/availableShipperOrder",
    name: "Orders for shipper",
    icon: "nc-icon nc-delivery-fast",
    component: AvailableShipperOrders,
    layout: "/admin"
  },
  // {
  //   path: "/clock",
  //   name: "Clock",
  //   icon: "nc-icon nc-time-alarm",
  //   component: ClockMain,
  //   layout: "/admin"
  // },
  // {
  //   path: "/tables",
  //   name: "Employees",
  //   icon: "nc-icon nc-tile-56",
  //   component: TableList,
  //   layout: "/admin"
  // },
  // {
  //   path: "/typography",
  //   name: "Products",
  //   icon: "nc-icon nc-caps-small",
  //   component: Typography,
  //   layout: "/admin"
  // },
  // {
  //   path: "/dailyordders",
  //   name: "Daily Orders",
  //   icon: "nc-icon nc-user-run",
  //   component: DailyOrders,
  //   layout: "/admin"
  // },
  // {
  //   path: "/workersprogress",
  //   name: "Workers Progress",
  //   icon: "nc-icon nc-sound-wave",
  //   component: WorkerProgress,
  //   layout: "/admin"
  // },
  // {
  //   path: "/icons",
  //   name: "Icons",
  //   icon: "nc-icon nc-diamond",
  //   component: Icons,
  //   layout: "/admin"
  // },
  // {
  //   path: "/pickerStage",
  //   name: "Material UI Table",
  //   icon: "nc-icon nc-controller-modern",
  //   component: CollapsibleTable,
  //   layout: "/admin"
  // },
  {
    path: "/products",
    name: "Products",
    icon: "nc-icon nc-layout-11",
    component: ProdctMain,
    layout: "/admin"
  },
  {
    path: "/employees",
    name: "Employees",
    icon: "nc-icon nc-single-02",
    component: EmployeeMain,
    layout: "/admin"
  }
 
  
];
export default routes;

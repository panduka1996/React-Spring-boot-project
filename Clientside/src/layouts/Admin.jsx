
import React from "react";
// javascript plugin used to create scrollbars on windows
import PerfectScrollbar from "perfect-scrollbar";
import { Route, Switch } from "react-router-dom";

import DemoNavbar from "components/Navbars/DemoNavbar.jsx";
import Footer from "components/Footer/Footer.jsx";
import Sidebar from "components/Sidebar/Sidebar.jsx";
import FixedPlugin from "components/FixedPlugin/FixedPlugin.jsx";

import MyContent from "../views/Icons"
import MoreDetails from "../views/supervisor/moreDetails.jsx"

import GetPickerOrderDetails from "../views/picker/getPickerOrderDetails.jsx"
import RemainOrders from "../views/picker/remainOrders.jsx"

import GetPackerOrderDetails from "../views/packer/getPackerOrderDetails.jsx"
import RemainPackerOrders from "../views/packer/ramainPackerOrders.jsx"

import GetShipperOrderDetails from "../views/shipper/getShipperOrderDetails.jsx"
import RemainShipperOrders from "../views/shipper/remainShipperOrders.jsx"

import AllocateNewOrders from "../views/supervisor/allocateNewOrders.jsx"
import NotAllowed from "../views/NotAllowed.jsx"
import ClockMain from "../views/clock/ClockMain.jsx"

import Actions from "../views/pickerAction/actions.jsx"

import routes from "routes.js";

var ps;

class Dashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      backgroundColor: "black",
      activeColor: "info"
    };
    this.mainPanel = React.createRef();
  }
  componentDidMount() {
    if (navigator.platform.indexOf("Win") > -1) {
      ps = new PerfectScrollbar(this.mainPanel.current);
      document.body.classList.toggle("perfect-scrollbar-on");
    }
  }
  componentWillUnmount() {
    if (navigator.platform.indexOf("Win") > -1) {
      ps.destroy();
      document.body.classList.toggle("perfect-scrollbar-on");
    }
  }
  componentDidUpdate(e) {
    if (e.history.action === "PUSH") {
      this.mainPanel.current.scrollTop = 0;
      document.scrollingElement.scrollTop = 0;
    }
  }
  handleActiveClick = color => {
    this.setState({ activeColor: color });
  };
  handleBgClick = color => {
    this.setState({ backgroundColor: color });
  };
  render() {
    return (
      <div className="wrapper">
        <Sidebar
          {...this.props}
          routes={routes}
          bgColor={this.state.backgroundColor}
          activeColor={this.state.activeColor}
        />
        <div className="main-panel" ref={this.mainPanel}>

          <DemoNavbar {...this.props} />
          <Switch>
            <Route path={this.props.match.path + '/edit/:id'} component={MyContent} />
            <Route path={this.props.match.path + '/moreDetails/:id'} component={MoreDetails} />
            <Route path={this.props.match.path + '/getPickerOrderDetails/:id'} component={GetPickerOrderDetails} />
            <Route path={this.props.match.path + '/remainOrders/:id'} component={RemainOrders} />
            <Route path={this.props.match.path + '/getPackerOrderDetails/:id'} component={GetPackerOrderDetails} />
            <Route path={this.props.match.path + '/remainPackerOrders/:id'} component={RemainPackerOrders} />
            <Route path={this.props.match.path + '/getShipperOrderDetails/:id'} component={GetShipperOrderDetails} />
            <Route path={this.props.match.path + '/remainShipperOrders/:id'} component={RemainShipperOrders} />
            <Route path={this.props.match.path + '/allocateNewOrders/:id'} component={AllocateNewOrders} />
            <Route path={this.props.match.path + '/notAllowed'} component={NotAllowed} />
            <Route path={this.props.match.path + '/operateClock'} component={ClockMain} />
            <Route path={this.props.match.path + '/actions/:id'} component={Actions} />

            {routes.map((prop, key) => {
              return (
                <Route
                  path={prop.layout + prop.path}
                  component={prop.component}
                  key={key}
                />
              );
            })}



          </Switch>
          <Footer fluid />
        </div>
        <FixedPlugin
          bgColor={this.state.backgroundColor}
          activeColor={this.state.activeColor}
          handleActiveClick={this.handleActiveClick}
          handleBgClick={this.handleBgClick}
        />
      </div>
    );
  }
}

export default Dashboard;

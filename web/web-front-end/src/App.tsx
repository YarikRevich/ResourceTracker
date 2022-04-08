
import React, { Dispatch } from 'react';
import { compose } from "redux"
import { connect } from "react-redux"
import { BrowserRouter, NavLink, Route, Routes } from "react-router-dom"
import { Row, Col } from 'react-flexbox-grid';

import Preloader from './components/Preloader/Preloader';
import Home from './components/Home/HomeContainer';
import Edit from './components/Edit/EditContainer';

import classes from "./constants/App/App.module.css"

import { createInitialized } from './redux/app-reducer';

import { State, Components } from './types';

class App extends React.Component<Components.App.AppType> {
	componentDidMount() {
		this.props.initialize()
	}

	render() {
		if (!this.props.initialized) {
			return <Preloader />
		}

		return (
			<BrowserRouter>
				<Row>
					<Col xs={12}>
						<Row start="xs">
							<Col xs={4}>
								<NavLink to="/"> <img className={classes["logo"]} src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFpo9KzQqdNNlp65pag7L98bwa4hS53J0Vznmi2S4rUDks6V9ubLVdYjORfGDhznPzpGE&usqp=CAU" alt="logo" /> </NavLink>
							</Col>
						</Row>
					</Col>
				</Row>

				<Routes>
					<Route path="/" element={<Home />} />
					<Route path="/edit">
						<Route path=":projectName" element={<Edit />} />
					</Route>
				</Routes>

				<Row className={classes["footer"]}>
					<Col xs={12}>
						<Row end="xs">
							<Col xs={4}>
								<div>
									<p>Created By <a href="https://github.com/YarikRevich"><b>@YarikRevich</b></a></p>
								</div>
							</Col>
						</Row>
					</Col>
				</Row>
			</BrowserRouter>
		)
	};
}


const mapStateToProps = (props: State) => {
	return {
		initialized: props.app.initialized
	}
}

const mapDispatchToProps = (dispatch: Dispatch<any>) => {
	return {
		initialize: () => {
			dispatch(createInitialized())
		}
	}
}

export default compose(connect(mapStateToProps, mapDispatchToProps))(App);

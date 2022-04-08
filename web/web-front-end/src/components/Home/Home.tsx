import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

import { Dropdown, DropdownButton } from 'react-bootstrap';
import Modal from '@bdenzer/react-modal';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Row, Col } from 'react-flexbox-grid';

import classes from "../../constants/Home/Home.module.css";

function createData(
	name: string,
	description: string
) {
	return { name, description };
}

const rows = [
	createData('Records', 'Checks if ... service is alive'),
];

export const Home = (props: any) => {
	useEffect(() => {
		props.getProjects()
	}, [])

	const navigate = useNavigate();
	const routeToEdit = (projectName: string) => {
		navigate(`/edit/${projectName}`);
	}

	return (
		<div>
			<Modal
				closeModal={props.setSelectedProjectStatusUnchecked}
				shouldShowModal={props.selectedProjectStatusChecked}
				title="Project status"
			>
				Status: {props.setSelectedProjectStatusUnchecked ? "working" : "not working"}
			</Modal>
			<Row>
				<Col xs={12}>
					<Row center="xs">
						<Col xs={6}>
							<h2>Projects</h2>
							<TableContainer sx={{ minHeight: "200px" }} className={classes["home-table"]} component={Paper}>
								<Table aria-label="simple table">
									<TableHead>
										<TableRow>
											<TableCell>Name</TableCell>
											<TableCell align="right">Description</TableCell>
											<TableCell align="right">Actions</TableCell>
										</TableRow>
									</TableHead>
									<TableBody>
										{rows.map((row) => (
											<TableRow
												key={row.name}
												sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
											>
												<TableCell component="th" scope="row">
													{row.name}
												</TableCell>
												<TableCell align="right">{row.description}</TableCell>
												<TableCell align="right">
													<DropdownButton onClick={() => console.log("log")} id="dropdown-basic-button" title="Actions">
														<Dropdown.Item onClick={() => routeToEdit(row.name)}>Edit</Dropdown.Item>
														<Dropdown.Item onClick={() => props.deleteProject()}>Delete</Dropdown.Item>
														<Dropdown.Divider />
														<Dropdown.Item onClick={() => props.checkSelectedProjectStatus()}>Status</Dropdown.Item>
													</DropdownButton>
												</TableCell>
											</TableRow>
										))}
									</TableBody>
								</Table>
							</TableContainer>
						</Col>
					</Row>
				</Col>
			</Row >
		</div>
	)
}

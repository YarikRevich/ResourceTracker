import React from "react";

import { Components } from "../../types"

import { useParams } from "react-router-dom";

import { Row, Col } from 'react-flexbox-grid';
import { Formik } from 'formik';
import * as Yup from 'yup';

import classes from "../../constants/Edit/Edit.module.css"


export const Edit = (props: any) => {
	const { projectName } = useParams();

	return (
		<Row>
			<Col xs={12}>
				<Row center="xs">
					<Col xs={6}>
						<Formik
							validationSchema={Yup.object().shape({
								projectName: Yup.string().min(1, "Invalid project name").required("Project name is required"),
								cloudProvider: Yup.string().oneOf(["aws", "gcp", "az"], "Choose valid cloud provider").required(),
								credentials: Yup.string().matches(/^(~\/\.|\/)[a-z]*.*\.(json|yaml)/, "Choose valid file with credentials").required(),
								profile: Yup.string().required("Profile is required"),
								region: Yup.string().withContext().required("Region if required"),
								zone: Yup.string().required(),
								email: Yup.string().email("Invalid email").required("Email is required"),
								reportFrequency: Yup.number().required("Report frequency is required"),
								reportFrequencyModifier: Yup.string().oneOf(["d", "m", "s"], "Choose valid report frequency modifier").required("Report frequency modifier is required"),
							})}
							initialValues={{
								projectName: "",
								cloudProvider: "none",
								credentials: "",
								profile: "",
								region: "",
								zone: "",
								email: "",
								reportFrequency: 0,
								reportFrequencyModifier: "",
							}}
							onSubmit={(values, { setSubmitting }) => {
								setTimeout(() => {
									alert(JSON.stringify(values, null, 2));
									setSubmitting(false);
								}, 400);
							}}
						>
							{({
								values,
								errors,
								touched,
								// handleChange,
								// handleBlur,
								// handleSubmit,
								// isSubmitting,
							}) => (
								<form>
									<span>Name</span>
									<div className="edit-form">
										<input type="text" name="project-name" />
									</div>

									<span>Addresses</span>
									<div className="edit-form">
										<Formik
											validationSchema={Yup.object().shape({
												tag: Yup.string().notRequired(),
												host: Yup.string().matches(/(http|https):\/\/[a-z]*\.[a-z]*/).required("Host is required"),
												path: Yup.string().required("Path is required"),
											})}
											initialValues={{
												tag: "",
												host: "",
												path: "",
											}}
											onSubmit={(values, { setSubmitting }) => {
												setTimeout(() => {
													setSubmitting(false);
												}, 400);
											}}
										>
											{({
												errors,
												touched,
												handleSubmit,
												handleChange,
											}) => (
												<form onSubmit={handleSubmit}>
													<input type="text" name="tag" onChange={handleChange} value={values.tag} />
													<input type="text" name="host" onChange={handleChange} value={values.host} />
													{errors.host && touched.host}
													<input type="text" name="path" onChange={handleChange} value={values.path} />
												</form>
											)} </Formik>
									</div>
									<span>Cloud provider</span>
									<select name="provider">
										<option value="none">---</option>
										{props.cloudProviders.map((v: string) => {
											<option value={v}>v.toUpperCase()</option>
										})}
									</select>
									{errors.cloudProviders && touched.cloudProviders}
									<div className="field padding-bottom--24">
										<input type="text" name="credentials" />
									</div>
									{values.cloudProvider == "aws" ? (
										<div className="field padding-bottom--24">
											<input type="text" name="profile" />
										</div>
									) : null}
									{values.cloudProvider == "gcp" ? (
										<>
											<div className="field padding-bottom--24">
												<input type="text" name="project" />
											</div>
											<div className="field padding-bottom--24">
												<input type="text" name="region" />
											</div>
											<div className="field padding-bottom--24">
												<input type="text" name="zone" />
											</div>
										</>
									) : null}
									{values.cloudProvider == "az" ? (
										<>
											<div className="field padding-bottom--24">
												<input type="text" name="project" />
											</div>
											<div className="field padding-bottom--24">
												<input type="text" name="region" />
											</div>
										</>
									) : null}

									<div className="field padding-bottom--24">
										<input type="email" name="email" />
									</div>

									<span>Report</span>

									<div className="field padding-bottom--24">
										<input type="number" name="frequency" />
									</div>

									<select name="frequency-modifier">
										<option value="none">---</option>
										{props.frequencyModifiers.map((v: string) => {
											<option value={v}>v.toUpperCase()</option>
										})}
									</select>

									<div className="field padding-bottom--24">
										<input type="submit" name="submit" value="Add" />
									</div>
								</form>
							)}
						</Formik>
					</Col>
				</Row>
			</Col >
		</Row >
	)
}

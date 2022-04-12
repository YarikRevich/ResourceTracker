import React, { Component } from "react";
import { Provider } from "react-redux";
import { render } from '@testing-library/react';
import { testStore } from "../redux/redux-store";

export const renderWithRedux = (component: any) => {
	return {
		...render(<Provider store={testStore}>component</Provider>)
	}
}

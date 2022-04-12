import React from 'react';
import { render } from '@testing-library/react';
import App from './App';
import { renderWithRedux } from "./utils/utils";

test('Renders and tests `App` component', () => {
	renderWithRedux(<App />)
});

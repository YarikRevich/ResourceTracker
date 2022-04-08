import React from 'react';
import axios from "axios";
import { render, screen } from '@testing-library/react';
import Edit from '../../components/Edit/EditContainer';
import { renderWithRedux } from "../utils/utils";

jest.mock("axios");

test('Renders and tests `Edit` component', () => {
	renderWithRedux(<Edit />);
});

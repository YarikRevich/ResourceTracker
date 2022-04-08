import React from "react"
import classes from "../../constants/Preloader/Preloader.module.css"
import { SpinnerCircular } from 'spinners-react';

const Preloader = () => {
	return (
		<div className={classes["spiner"]}>
			<SpinnerCircular size="50" color="#800000" enabled={true} />
		</div>
	)
}

export default Preloader

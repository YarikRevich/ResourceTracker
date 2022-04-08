import React from "react"
import { connect } from "react-redux"
import { Redirect } from "react-router"
import type { HOCs } from "./../types"

const withAuth = (Componenent: any) => {
    const f = (props: HOCs.WithAuth) => {
        if (!props.isAuthed) return <Redirect to={"demo"} />
        return <Componenent {...props} />
    }
    return connect(mapStateToProps, {})(f)
}

const mapStateToProps = (props: any) => {
    return {
        isAuthed: props.authentication.isAuthed
    }
}

export { withAuth }
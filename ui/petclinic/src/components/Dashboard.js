import React from 'react'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Sidebar from './Sidebar'
import MainDashboard from './MainDashboard'
const Dashboard = (props) => {
    return (
        <Container fluid='true'>
            <Row>
                <Sidebar />
                {props.children}
            </Row>
        </Container>
    )
}

export default Dashboard;
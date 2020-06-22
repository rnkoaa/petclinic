import React from "react";
import "./App.scss";
import { BrowserRouter as Router } from "react-router-dom";
import { RecoilRoot } from "recoil";
import MainLayout from "./shared/layouts/MainLayout";

function App() {
    return (
        <RecoilRoot>
            <Router>
                <MainLayout />
            </Router>
        </RecoilRoot>
    );
}

export default App;

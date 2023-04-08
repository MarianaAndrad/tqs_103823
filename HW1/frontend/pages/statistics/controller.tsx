import {useEffect, useState} from "react";
import React from 'react';

export default function Controller({ backend }: { backend: string }) {
    const [successfulRequests, setSuccessfulRequests] = useState(0);
    const [failedRequests, setFailedRequests] = useState(0);

    const [successRate, setSuccessRate] = useState(0);
    const [failRate, setFailRate] = useState(0);

    const requestStats = () => {
        fetch(backend + "/api/v1/statistics")
            .then(res => res.json())
            .then(data => {
                setSuccessRate(data.successfulRequests / (data.successfulRequests + data.failedRequests) * 100);
                setFailRate(data.failedRequests / (data.successfulRequests + data.failedRequests) * 100);

                setSuccessfulRequests(data.successfulRequests);
                setFailedRequests(data.failedRequests);
            });
    };

    useEffect(() => {
        requestStats();
        setInterval(requestStats, 1000);
    }, []);

    return (
        <div className="p-6 h-screen">
            <h1 className="text-3xl font-bold mb-6 p-10">Controller Statistics</h1>

            <div className="grid grid-cols-2 gap-6">
                <div className="bg-white rounded-lg shadow p-6">
                    <h2 className="text-xl font-bold mb-4">Failed requests</h2>
                    <p className="text-red-700 text-3xl font-bold">{failedRequests}</p>
                </div>

                <div className="bg-white rounded-lg shadow p-6">
                    <h2 className="text-xl font-bold mb-4">Successful Requests</h2>
                    <p className="text-green-600 text-3xl font-bold">{successfulRequests}</p>
                </div>

                <div className="bg-white rounded-lg shadow p-6">
                    <h2 className="text-xl font-bold mb-4">Request fail rate</h2>
                    <div className="radial-progress text-red-700 color-red-700" style={{"--value": failRate} as React.CSSProperties}>{Math.round(failRate)}%</div>
                    <div className="stat-desc mt-2" >{failRate.toFixed(2)}% </div>

                </div>

                <div className="bg-white rounded-lg shadow p-6">
                    <h2 className="text-xl font-bold mb-4">Request success rate</h2>
                    {/*<p className="text-green-600 text-3xl font-bold">{successRate.toFixed(2)}%</p>*/}
                    <div className="radial-progress text-green-600 color-green-600" style={{"--value": successRate} as React.CSSProperties}>{Math.round(successRate)}%</div>
                    <div className="stat-desc mt-2">{successRate.toFixed(2)}%</div>
                </div>
            </div>
        </div>
    );
}

export function getServerSideProps() {
    return {
        props: {
            backend: process.env.APP_BACKEND_URL ? process.env.APP_BACKEND_URL : "http://localhost:8080"
        },
    };
}
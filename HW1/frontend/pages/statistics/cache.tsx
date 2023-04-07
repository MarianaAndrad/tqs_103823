import {useEffect, useState} from "react";
import React from 'react';

export default function Cache({ backend }: { backend: string }) {
    const [cacheHits, setCacheHits] = useState(0);
    const [cacheMisses, setCacheMisses] = useState(0);

    const [successRate, setSuccessRate] = useState(0);
    const [failRate, setFailRate] = useState(0);

    const requestStats = () => {
        fetch(backend + "/api/v1/statistics")
            .then(res => res.json())
            .then(data => {
                setSuccessRate(data.cacheHits / (data.cacheHits + data.cacheMisses) * 100);
                setFailRate(data.cacheMisses / (data.cacheHits + data.cacheMisses) * 100);

                setCacheHits(data.cacheHits);
                setCacheMisses(data.cacheMisses);
            });
    };

    useEffect(() => {
        requestStats();
        setInterval(requestStats, 1000);
    }, []);



    return (
        <div className="p-6 h-screen">
            <h1 className="text-3xl font-bold mb-6 p-10">Cache Statistics</h1>

            <div className="grid grid-cols-2 gap-6">
                <div className="bg-white rounded-lg shadow p-6">
                    <h2 className="text-xl font-bold mb-4">Cache Misses</h2>
                    <p className="text-red-700 text-3xl font-bold">{cacheMisses}</p>
                </div>

                <div className="bg-white rounded-lg shadow p-6">
                    <h2 className="text-xl font-bold mb-4">Cache Hits</h2>
                    <p className="text-green-600 text-3xl font-bold">{cacheHits}</p>
                </div>

                <div className="bg-white rounded-lg shadow p-6">
                    <h2 className="text-xl font-bold mb-4">Cache miss rate</h2>
                    <div className="radial-progress text-red-700 color-red-700" style={{"--value": failRate} as React.CSSProperties}>{Math.round(failRate)}%</div>
                    <div className="stat-desc mt-2" >{failRate.toFixed(2)}% </div>

                </div>

                <div className="bg-white rounded-lg shadow p-6">
                    <h2 className="text-xl font-bold mb-4">Cache hit rate</h2>
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
            backend: process.env.APP_BACKEND_URL
        },
    };
}
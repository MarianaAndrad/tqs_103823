import React, {useEffect, useState} from "react";

export default function Api({ backend }: { backend: string }) {
    const [successfulGeocodingRequests, setSuccessfulGeocodingRequests] = useState(0);
    const [failedGeocodingRequests, setFailedGeocodingRequests] = useState(0);

    const [successfulAirVisualRequests, setSuccessfulAirVisualRequests] = useState(0);
    const [failedAirVisualRequests, setFailedAirVisualRequests] = useState(0);

    const [successfulOpenWeatherRequests, setSuccessfulOpenWeatherRequests] = useState(0);
    const [failedOpenWeatherRequests, setFailedOpenWeatherRequests] = useState(0);

    const [sucessRateOpenWeather, setSucessRateOpenWeather] = useState(0);
    const [failRateOpenWeather, setFailRateOpenWeather] = useState(0);

    const [sucessRateAirVisual, setSucessRateAirVisual] = useState(0);
    const [failRateAirVisual, setFailRateAirVisual] = useState(0);

    const [sucessRateGeocoding, setSucessRateGeocoding] = useState(0);
    const [failRateGeocoding, setFailRateGeocoding] = useState(0);

    const requestStats = () => {
        fetch(backend + "/api/v1/statistics")
            .then(res => res.json())
            .then(data => {
                if (data.successfulOpenWeatherRequests + data.failedOpenWeatherRequests == 0){
                    setSucessRateOpenWeather(0);
                    setFailRateOpenWeather(0);
                }else{
                    setSucessRateOpenWeather(data.successfulOpenWeatherRequests / (data.successfulOpenWeatherRequests + data.failedOpenWeatherRequests) * 100);
                    setFailRateOpenWeather(data.failedOpenWeatherRequests / (data.successfulOpenWeatherRequests + data.failedOpenWeatherRequests) * 100);
                }

                if( data.successfulAirVisualRequests + data.failedAirVisualRequests == 0){
                    setSucessRateAirVisual(0);
                    setFailRateAirVisual(0);
                }else{
                    setSucessRateAirVisual(data.successfulAirVisualRequests / (data.successfulAirVisualRequests + data.failedAirVisualRequests) * 100);
                    setFailRateAirVisual(data.failedAirVisualRequests / (data.successfulAirVisualRequests + data.failedAirVisualRequests) * 100);
                }
                if (data.successfulGeocodingRequests + data.failedGeocodingRequests == 0) {
                    setSucessRateGeocoding(0);
                    setFailRateGeocoding(0);
                } else {
                    setSucessRateGeocoding(data.successfulGeocodingRequests / (data.successfulGeocodingRequests + data.failedGeocodingRequests) * 100);
                    setFailRateGeocoding(data.failedGeocodingRequests / (data.successfulGeocodingRequests + data.failedGeocodingRequests) * 100);
                }

                setSuccessfulGeocodingRequests(data.successfulGeocodingRequests);
                setFailedGeocodingRequests(data.failedGeocodingRequests);

                setSuccessfulAirVisualRequests(data.successfulAirVisualRequests);
                setFailedAirVisualRequests(data.failedAirVisualRequests);

                setSuccessfulOpenWeatherRequests(data.successfulOpenWeatherRequests);
                setFailedOpenWeatherRequests(data.failedOpenWeatherRequests);
            });
    };

    useEffect(() => {
        requestStats();
        setInterval(requestStats, 1000);
    }, []);


    return (
        <div className="pt-20 h-screen">
            <h1 className="text-3xl font-bold mb-6 p-5">API Statistics</h1>

            <h1 className="p-5"></h1>
            <div className="stats shadow grid grid-cols-4">
                <div className="stat">
                    <div className="stat-figure">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on  text-primary">✅</div>
                        </label>
                    </div>
                    <div className="stat-title">Open Weather</div>
                    <div className="stat-value text-primary">{successfulOpenWeatherRequests}</div>
                    <div className="stat-desc">API successful requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure ">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on text-secondary">X</div>
                        </label>
                    </div>
                    <div className="stat-title">Open Weather</div>
                    <div className="stat-value text-secondary">{failedOpenWeatherRequests}</div>
                    <div className="stat-desc">API failed requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on  text-primary">‰</div>
                        </label>
                    </div>
                    <div className="stat-title">Open Weather</div>
                    <div className="stat-value  text-primary">{sucessRateOpenWeather.toFixed(2)}</div>
                    <div className="stat-desc">API success rate</div>
                </div>

                <div className="stat">
                    <div className="stat-figure">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on">‰</div>
                        </label>
                    </div>
                    <div className="stat-title">Open Weather</div>
                    <div className="stat-value">{failRateOpenWeather.toFixed(2)}</div>
                    <div className="stat-desc">API fail rate</div>
                </div>
            </div>

            <h1 className="p-5"></h1>
            <div className="stats shadow grid grid-cols-4">
                <div className="stat">
                    <div className="stat-figure">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on  text-primary">✅</div>
                        </label>
                    </div>
                    <div className="stat-title">Air Visual</div>
                    <div className="stat-value text-secondary">{successfulAirVisualRequests}</div>
                    <div className="stat-desc">API successful requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure ">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on text-secondary">X</div>
                        </label>
                    </div>
                    <div className="stat-title">Air Visual</div>
                    <div className="stat-value text-primary">{failedAirVisualRequests}</div>
                    <div className="stat-desc">API failed requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure ">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on">‰</div>
                        </label>
                    </div>
                    <div className="stat-title">Air Visual</div>
                    <div className="stat-value">{sucessRateAirVisual.toFixed(2)}</div>
                    <div className="stat-desc">API success rate</div>
                </div>

                <div className="stat">
                    <div className="stat-figure">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on">‰</div>
                        </label>
                    </div>
                    <div className="stat-title">Air Visual</div>
                    <div className="stat-value  text-primary">{failRateAirVisual.toFixed(2)}</div>
                    <div className="stat-desc">API fail rate</div>
                </div>
            </div>

            <h1 className="p-5"></h1>
            <div className="stats shadow grid grid-cols-4">
                <div className="stat">
                    <div className="stat-figure">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on  text-primary">✅</div>
                        </label>
                    </div>
                    <div className="stat-title">Geocoding</div>
                    <div className="stat-value  text-primary">{successfulGeocodingRequests}</div>
                    <div className="stat-desc">API successful requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure ">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on text-secondary">X</div>
                        </label>
                    </div>
                    <div className="stat-title">Geocoding</div>
                    <div className="stat-value text-secondary">{failedGeocodingRequests}</div>
                    <div className="stat-desc">API failed requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on  text-primary">‰</div>
                        </label>
                    </div>
                    <div className="stat-title">Geocoding</div>
                    <div className="stat-value  text-primary">{sucessRateGeocoding.toFixed(2)}</div>
                    <div className="stat-desc">API success rate</div>
                </div>

                <div className="stat">
                    <div className="stat-figure ">
                        <label className="swap swap-active text-6xl">
                            <div className="swap-on text-secondary">‰</div>
                        </label>
                    </div>
                    <div className="stat-title">Geocoding</div>
                    <div className="stat-value text-secondary">{failRateGeocoding.toFixed(2)}%</div>
                    <div className="stat-desc">API fail rate</div>
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
import {useEffect, useState} from "react";

export default function Api() {
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
        fetch("http://localhost:8080/api/v1/statistics")
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
            <div className="stats shadow grid grid-cols-4">
                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                    </div>
                    <div className="stat-title">Open Weather</div>
                    <div className="stat-value">{successfulOpenWeatherRequests}</div>
                    <div className="stat-desc">API successful requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"></path></svg>
                    </div>
                    <div className="stat-title">Open Weather</div>
                    <div className="stat-value">{failedOpenWeatherRequests}</div>
                    <div className="stat-desc">API failed requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path></svg>
                    </div>
                    <div className="stat-title">Open Weather</div>
                    <div className="stat-value">{sucessRateOpenWeather.toFixed(2)}</div>
                    <div className="stat-desc">API success rate</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path></svg>
                    </div>
                    <div className="stat-title">Open Weather</div>
                    <div className="stat-value">{failRateOpenWeather.toFixed(2)}</div>
                    <div className="stat-desc">API fail rate</div>
                </div>
            </div>

            <div className="stats shadow grid grid-cols-4">
                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                    </div>
                    <div className="stat-title">Air Visual</div>
                    <div className="stat-value">{successfulAirVisualRequests}</div>
                    <div className="stat-desc">API successful requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"></path></svg>
                    </div>
                    <div className="stat-title">Air Visual</div>
                    <div className="stat-value">{failedAirVisualRequests}</div>
                    <div className="stat-desc">API failed requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path></svg>
                    </div>
                    <div className="stat-title">Air Visual</div>
                    <div className="stat-value">{sucessRateAirVisual.toFixed(2)}</div>
                    <div className="stat-desc">API success rate</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path></svg>
                    </div>
                    <div className="stat-title">Air Visual</div>
                    <div className="stat-value">{failRateAirVisual.toFixed(2)}</div>
                    <div className="stat-desc">API fail rate</div>
                </div>
            </div>

            <div className="stats shadow grid grid-cols-4">
                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                    </div>
                    <div className="stat-title">Geocoding</div>
                    <div className="stat-value">{successfulGeocodingRequests}</div>
                    <div className="stat-desc">API successful requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"></path></svg>
                    </div>
                    <div className="stat-title">Geocoding</div>
                    <div className="stat-value">{failedGeocodingRequests}</div>
                    <div className="stat-desc">API failed requests</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path></svg>
                    </div>
                    <div className="stat-title">Geocoding</div>
                    <div className="stat-value">{sucessRateGeocoding.toFixed(2)}</div>
                    <div className="stat-desc">API success rate</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="inline-block w-8 h-8 stroke-current"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path></svg>
                    </div>
                    <div className="stat-title">Geocoding</div>
                    <div className="stat-value">{failRateGeocoding.toFixed(2)}</div>
                    <div className="stat-desc">API fail rate</div>
                </div>
            </div>
        </div>
    );
}
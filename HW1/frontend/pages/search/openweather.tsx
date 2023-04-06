import React, {useEffect, useState} from "react";
import Image from "next/image";

export default function OpenWeather() {

    const [country, setCountry] = useState("");
    const [city, setCity] = useState("");

    const [apiError, setApiError] = useState(false);
    const [coord, setcoor] = useState([]);
    const [airQualityData, setAirQualityData] = useState([]);

    const handleSearch = () => {
        fetch(`http://localhost:8080/api/v1/${country}/${city}/geocoding`)
            .then((res) => res.json())
            .then((data) => {
                console.log(data);
                setcoor(data);
            })
            .catch((err) => setApiError(true));
    };

    const Other = (data) => {
        if (data && data["lat"] && data["lon"]) {
            const lat = data["lat"];
            const lon = data["lon"];
            fetch(`http://localhost:8080/api/v1/${lat}/${lon}/air-quality`)
                .then((res) => res.json())
                .then((data) => {
                    console.log(data);
                    setAirQualityData(data);
                })
                .catch((err) => setApiError(true));
        }
    };

    const handleSearchButtonClick = () => {
        handleSearch();
        Other(coord);
    }

    return (
        <>
            {apiError &&
                <div className="alert alert-error shadow-lg mt-10">
                    <div>
                        <svg onClick={e => setApiError(false)} xmlns="http://www.w3.org/2000/svg" className="cursor-pointer stroke-current flex-shrink-0 h-6 w-6"
                             fill="none" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                        </svg>
                        <span>Not Found Info or Internal Server Error</span>
                    </div>
                </div>
            }
            {(!airQualityData["latitude"]) && (
                    <div className="container h-screen  py-8 pt-20">
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 py-8">
                            <input name="city" type="text" placeholder="City" className="input input-bordered input-secondary w-full" value={city} onChange={(e) => setCity(e.target.value)}/>
                            <input name="country" type="text" placeholder="Country" className="input input-bordered input-secondary w-full " value={country} onChange={(e) => setCountry(e.target.value)}/>
                            <button className="btn btn-primary max-w-xs" onClick={handleSearchButtonClick}>Search</button>
                        </div>
                    </div>
                )
            }

            {
                airQualityData["latitude"]  && (
                    <div className="container  h-screen py-8">
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 py-8">
                            <input name="city" type="text" placeholder="City" className="input input-bordered input-secondary w-full" value={city} onChange={(e) => setCity(e.target.value)}/>
                            <input name="country" type="text" placeholder="Country" className="input input-bordered input-secondary w-full " value={country} onChange={(e) => setCountry(e.target.value)}/>
                            <button className="btn btn-primary max-w-xs" onClick={handleSearchButtonClick}>Search</button>
                        </div>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 py-8">
                            <div className="stats shadow">
                                <div className="stat-figure text-secondary">
                                    <label className="swap swap-active text-6xl">
                                        <div className="swap-on">📌️</div>
                                    </label>
                                </div>
                                <div className="stat">
                                    <div className="stat-title">Coordenates Information</div>
                                    <div className="stat-value">({airQualityData["latitude"].toFixed(2)}, {airQualityData["longitude"].toFixed(2)}) </div>
                                </div>
                            </div>
                            <div className="stats shadow">
                                <div className="stat-figure text-secondary">
                                    <label className="swap swap-active text-6xl">
                                        <div className="swap-on">🌐</div>
                                    </label>
                                </div>
                                <div className="stat">
                                    <div className="stat-title">Location Information</div>
                                    <div className="stat-value">{city}</div>
                                    <div className="stat-desc">In {country} </div>
                                </div>
                            </div>
                            <div className="stats shadow">
                                <div className="stat-figure">
                                    <label className="swap swap-active text-6xl">
                                        <div className="swap-on"> ♻ </div>
                                    </label>
                                </div>
                                <div className="stat">
                                    <div className="stat-title">Air Quality Index (AQI)</div>
                                    <div className="stat-value">{airQualityData["aqi"]}</div>
                                    <div className="stat-desc">{airQualityData["date"]}</div>
                                </div>
                            </div>
                        </div>
                        <h1 className="text-2xl font-bold text-center mb-8">Pollutant Concentrations</h1>
                        <div className="grid grid-cols-1 md:grid-cols-1 gap-8 ">

                            <div className="stats shadow">
                                <div className="stat">
                                    <div className="stat-figure text-secondary">
                                        <label className="swap swap-active text-6xl">
                                            <div className="swap-on">CO</div>
                                        </label>
                                    </div>
                                    <div className="stat-title">Carbon Monoxide</div>
                                    <div className="stat-value text-accent">{airQualityData["co"]}</div>
                                </div>

                                <div className="stat">
                                    <div className="stat-figure text-secondary">
                                        <label className="swap swap-active text-6xl">
                                            <div className="swap-on text-primary">NO</div>
                                        </label>
                                    </div>
                                    <div className="stat-title">Nitrogen Oxide</div>
                                    <div className="stat-value">{airQualityData["no"]}</div>

                                </div>

                                <div className="stat">
                                    <div className="stat-figure text-secondary">
                                        <label className="swap swap-active text-6xl">
                                            <div className="swap-on text-accent">NH3</div>
                                        </label>
                                    </div>
                                    <div className="stat-title">Ammonia</div>
                                    <div className="stat-value text-primary">{airQualityData["nh3"]}</div>
                                </div>

                                <div className="stat">
                                    <div className="stat-figure text-secondary">
                                        <label className="swap swap-active text-6xl">
                                            <div className="swap-on">NO2</div>
                                        </label>
                                    </div>
                                    <div className="stat-title">Nitrogen Dioxide</div>
                                    <div className="stat-value">{airQualityData["no2"]}</div>
                                </div>

                            </div>
                            <div className="stats shadow">
                                <div className="stat">
                                    <div className="stat-figure text-secondary">
                                        <label className="swap swap-active text-6xl">
                                            <div className="swap-on">O3</div>
                                        </label>
                                    </div>
                                    <div className="stat-title">Ozone</div>
                                    <div className="stat-value">{airQualityData["o3"]}</div>
                                </div>

                                <div className="stat">
                                    <div className="stat-figure text-secondary">
                                        <label className="swap swap-active text-6xl">
                                            <div className="swap-on">SO2</div>
                                        </label>
                                    </div>
                                    <div className="stat-title">Sulfur Dioxide</div>
                                    <div className="stat-value text-primary">{airQualityData["so2"]}</div>
                                </div>

                                <div className="stat">
                                    <div className="stat-title">PM2.5</div>
                                    <div className="stat-value">{airQualityData["pm2_5"]}</div>
                                    <div className="stat-desc text-accent">Partículas finas com diâmetro menor ou igual a 2.5 micrômetros </div>
                                </div>

                                <div className="stat">
                                    <div className="stat-title">PM10</div>
                                    <div className="stat-value">{airQualityData["pm10"]}</div>
                                    <div className="stat-desc text-accent">Partículas finas com diâmetro menor ou igual a 10 micrômetros </div>
                                </div>

                            </div>


                        </div>
                    </div>
                )
            }


        </>
    )
}
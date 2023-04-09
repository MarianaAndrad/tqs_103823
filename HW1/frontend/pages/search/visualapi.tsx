import Link from "next/link";
import React, {useEffect, useRef, useState} from "react";
import img from "@/pages/search/imageWeather.png";

interface WeatherData {
    city: string,
    latitude: number,
    longitude: number,
    country: string,
    state: string,
    date: string,
    temperature: number,
    humidity: number,
    windSpeed: number,
    windDirection: number,
    pressure: number,
}

export default function VisualAPI({ backend }: { backend: string }) {
    const [apiError, setApiError] = useState(false);
    const [toManyRequest, setToManyRequest] = useState(false);
    const [searching , setSearching] = useState(true);

    const [allCountry, setAllCountry] = useState([]);
    const [allState, setAllState] = useState([]);
    const [allCity, setAllCity] = useState([]);

    const [country, setCountry] = useState("");
    const [state, setState] = useState("");
    const [city, setCity] = useState("");

    const stateRef = useRef(null);
    const cityRef = useRef(null);

    const [weatherData, setWeatherData] = useState<WeatherData | null>(null);

    useEffect(() => {
        fetch(backend + "/api/v1/countries")
            .then(res => res.json())
            .then(data => {
                setAllCountry(data);
            })
            .catch(err => setToManyRequest(true));
    }, []);

    const stateFetch = (countrydata: string) => {
        fetch(backend + "/api/v1/" + countrydata + "/states")
            .then(res => res.json())
            .then(data => {
                setAllState(data);
            })
            .catch(err => setToManyRequest(true));

        // @ts-ignore
        stateRef.current.selectedIndex = 0;
        setState("");
        setCity("")
        // @ts-ignore
        cityRef.current.selectedIndex = 0;
    }
    const cityFetch = (countrydata: string, statedata: string) => {
        fetch(backend + "/api/v1/" + countrydata + "/" + statedata + "/cities")
            .then(res => res.json())
            .then(data => {
                setAllCity(data);
            })
            .catch(err => setToManyRequest(true));

        // @ts-ignore
        cityRef.current.selectedIndex = 0;
        setCity("");
    }
    const handleSearch = () => {
        fetch(backend + "/api/v1/" + country + "/" + state + "/" + city + "/weather")
            .then(res => res.json())
            .then(data => {
                console.log(data);
                setWeatherData(data);
            })
            .catch(err => setApiError(true));
    };

    const handleSearchButtonClick = () => {
        handleSearch();
        setSearching(false);
    }
    const selectCountry = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setCountry(e.target.value);
        stateFetch(e.target.value);
        //remover o states existentes
        setAllState([]);
    }

    const selectState = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setState(e.target.value);
        cityFetch(country, e.target.value);
        //remover o cities existentes
        setAllCity([]);
    }

    const reset = () => {
        setSearching(true);
        setWeatherData(null);
        setCountry("");
        setState("");
        setCity("");

    }

    return (
        <>
            { searching && (
            <div className="container h-screen">
                <div className="min-h-screen bg-base-100 pt-20">
                        <h1 className="text-5xl font-bold text-primary my-8">Weather Search</h1>
                        <select name="country" className="select select-info w-1/2 max-w-md mb-2" onChange={e => selectCountry(e)}>
                            <option disabled selected>Select Country</option>
                            {allCountry.map((val, i) =>
                                <option value={val} key={i}>{val}</option>
                            )}
                        </select>

                        <select disabled={country === ""} ref={stateRef} name="state" className="select select-info w-1/2 max-w-md mb-2" onChange={e => selectState(e)}>
                            <option disabled selected>Select State</option>
                            {allState.map((val, i) =>
                                <option value={val} key={i}>{val}</option>
                            )}
                        </select>

                        <select disabled={state === ""} ref={cityRef} name="city" className="select select-info w-1/2 max-w-md mb-4" onChange={e => setCity(e.target.value)}>
                            <option disabled selected>Select City</option>
                            {allCity.map((val, i) =>
                                <option value={val} key={i}>{val}</option>
                            )}
                        </select>

                    {(country !== "" && state !== "" && city !== "" ) &&
                        <button className="btn btn-outline btn-success"
                                onClick={handleSearchButtonClick}>Submit</button>
                    }

                    {toManyRequest &&
                        <div className="alert alert-error shadow-lg mt-10">
                            <div>
                                <svg onClick={e => setApiError(false)} xmlns="http://www.w3.org/2000/svg" className="cursor-pointer stroke-current flex-shrink-0 h-6 w-6"
                                     fill="none" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                                </svg>
                                <span>Internal Server Error</span>
                            </div>
                        </div>
                    }

                </div>
            </div>
            )}


            { !searching && weatherData && (
                <div className="container h-screen py-8 pt-20 mx-auto">
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-8 py-8">
                        <div className="stats shadow">
                            <div className="stat-figure text-secondary">
                                <label className="swap swap-active text-6xl">
                                    <div className="swap-on">📌️</div>
                                </label>
                            </div>
                            <div className="stat">
                                <div className="stat-title">Coordenates Information</div>
                                <div className="stat-value">latitude: {weatherData["latitude"]}</div>
                                <div className="stat-value">longitude: {weatherData["longitude"]}</div>
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
                                <div className="stat-value">{weatherData["city"]}</div>
                                <div className="stat-desc">In {weatherData["country"]} and {weatherData["state"]}</div>
                            </div>
                        </div>
                        <div className="stats shadow">
                            <div className="stat-figure text-secondary">
                                <label className="swap swap-active text-6xl">
                                    <div className="swap-on">⏳</div>
                                </label>
                            </div>
                            <div className="stat">
                                <div className="stat-title">Date</div>
                                <div className="stat-value">{weatherData["date"]}</div>
                            </div>
                        </div>
                    </div>
                    <h1 className="text-2xl font-bold text-center mb-8">Weather Information</h1>


                    <div className="stats shadow">

                        <div className="stat">
                            <div className="stat-figure text-secondary">
                                <label className="swap swap-active text-6xl">
                                    <div className="swap-on">🌡️</div>
                                </label>
                            </div>
                            <div className="stat-title">Temperature</div>
                            <div className="stat-value text-primary">{weatherData["temperature"]}ºC</div>
                        </div>

                        <div className="stat">
                            <div className="stat-figure text-secondary">
                                <label className="swap swap-active text-6xl">
                                    <div className="swap-on">🌬️</div>
                                </label>
                            </div>
                            <div className="stat-title">Pressure</div>
                            <div className="stat-value text-secondary">{weatherData["pressure"]}</div>
                        </div>

                        <div className="stat">
                            <div className="stat-figure text-secondary">
                                <label className="swap swap-active text-6xl">
                                    <div className="swap-on">💧</div>
                                </label>
                            </div>
                            <div className="stat-title">Humidity</div>
                            <div className="stat-value">{weatherData["humidity"]}</div>
                        </div>

                        <div className="stat">
                            <div className="stat-figure text-secondary">
                                <label className="swap swap-active text-6xl">
                                    <div className="swap-on">🌬️</div>
                                </label>
                            </div>
                            <div className="stat-title">Wind Speed</div>
                            <div className="stat-value text-primary">{weatherData["windSpeed"]}</div>
                        </div>

                        <div className="stat">
                            <div className="stat-figure text-secondary">
                                <label className="swap swap-active text-6xl">
                                    <div className="swap-on">🍃</div>
                                </label>
                            </div>
                            <div className="stat-title">Wind Direction</div>
                            <div className="stat-value text-secondary">{weatherData["windDirection"]}</div>
                        </div>



                    </div>

                    <div className="container pt-6 ">
                        <Link className="btn btn-outline" href="/search/visualapi" onClick={_ => reset()}>Previous page</Link>
                    </div>

                    {apiError &&
                    <div className="alert alert-error shadow-lg mt-10">
                        <div>
                            <svg onClick={e => setApiError(false)} xmlns="http://www.w3.org/2000/svg" className="cursor-pointer stroke-current flex-shrink-0 h-6 w-6"
                                 fill="none" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                            </svg>
                            <span>Internal Server Error</span>
                        </div>
                    </div>
                }
                </div>
                )
            }


        </>
    )
}


export function getServerSideProps() {
    return {
        props: {
            backend: process.env.APP_BACKEND_URL ? process.env.APP_BACKEND_URL : "http://localhost:8080"
        },
    };
}
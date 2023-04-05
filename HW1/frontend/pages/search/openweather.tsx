import Link from "next/link";
import React, {useEffect, useRef, useState} from "react";

export default function OpenWeather() {
    const [lat, setLat] = useState(0);
    const [lon, setLon] = useState(0);

    const [apiError, setApiError] = useState(false);

    const [country, setCountry] = useState("");
    const [city, setCity] = useState("");
    const [pollution, setPollution] = useState(false);
    const [weather, setWeather] = useState(false);

    return (
        <>
            <div className="p-6 h-screen">
                <div className="min-h-screen bg-base-100 pt-20">
                    <form action="/home/mariana/Universidade/Lei-TQS/HW1/airQuality/frontend/pages/resultsInfo" method="GET" className="flex flex-col items-center">
                        <h1 className="text-5xl font-bold text-primary my-8">Air Quality </h1>
                        <div className="flex w-full max-w-md bg-base-100 ">
                            <input type="text" placeholder="Country" className="input input-bordered input-info w-full max-w-xs" />
                            <input type="text" placeholder="City" className="input input-bordered input-info w-full max-w-xs" />
                            <button className="btn btn-success btn-accent">Success</button>
                            <button className="btn  btn-info loading">loading</button>
                        </div>

                        <div className="flex flex-col w-full lg:flex-row">
                            <div className="grid h-80 card bg-base-600 rounded-box place-items-center">
                                <div className="stats shadow h-40">

                                    <div className="stat">
                                        <div className="stat-title">Total Page Views</div>
                                        <div className="stat-value">89,400</div>
                                        <div className="stat-desc">21% more than last month</div>
                                    </div>

                                </div>
                            </div>

                            <div className="grid flex-grow h-60 card bg-base-300 rounded-box place-items-center">
                                <div className="stats shadow">

                                    <div className="stat">
                                        <div className="stat-figure text-primary">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                 className="inline-block w-8 h-8 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                                            </svg>
                                        </div>
                                        <div className="stat-title">Total Likes</div>
                                        <div className="stat-value text-primary">25.6K</div>
                                        <div className="stat-desc">21% more than last month</div>
                                    </div>

                                    <div className="stat ">
                                        <div className="stat-figure text-secondary">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                 className="inline-block w-8 h-8 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                                            </svg>
                                        </div>
                                        <div className="stat-title">Page Views</div>
                                        <div className="stat-value text-secondary">2.6M</div>
                                        <div className="stat-desc">21% more than last month</div>
                                    </div>

                                    <div className="stat">

                                        <div className="stat-figure text-secondary">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                 className="inline-block w-8 h-8 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                                            </svg>
                                        </div>
                                        <div className="stat-value">86%</div>
                                        <div className="stat-title">Tasks done</div>
                                        <div className="stat-desc text-secondary">31 tasks remaining</div>
                                    </div>

                                </div>
                                <div className="stats shadow">

                                    <div className="stat">
                                        <div className="stat-figure text-primary">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                 className="inline-block w-8 h-8 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                                            </svg>
                                        </div>
                                        <div className="stat-title">Total Likes</div>
                                        <div className="stat-value text-primary">25.6K</div>
                                        <div className="stat-desc">21% more than last month</div>
                                    </div>

                                    <div className="stat ">
                                        <div className="stat-figure text-secondary">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                 className="inline-block w-8 h-8 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                                            </svg>
                                        </div>
                                        <div className="stat-title">Page Views</div>
                                        <div className="stat-value text-secondary">2.6M</div>
                                        <div className="stat-desc">21% more than last month</div>
                                    </div>

                                    <div className="stat">

                                        <div className="stat-figure text-secondary">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                 className="inline-block w-8 h-8 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                                            </svg>
                                        </div>
                                        <div className="stat-value">86%</div>
                                        <div className="stat-title">Tasks done</div>
                                        <div className="stat-desc text-secondary">31 tasks remaining</div>
                                    </div>

                                    <div className="stat">

                                        <div className="stat-figure text-secondary">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                 className="inline-block w-8 h-8 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                                            </svg>
                                        </div>
                                        <div className="stat-value">86%</div>
                                        <div className="stat-title">Tasks done</div>
                                        <div className="stat-desc text-secondary">31 tasks remaining</div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        {/*{apiError &&*/}
                        {/*    <div className="alert alert-error shadow-lg mt-10">*/}
                        {/*        <div>*/}
                        {/*            <svg onClick={e => setApiError(false)} xmlns="http://www.w3.org/2000/svg" className="cursor-pointer stroke-current flex-shrink-0 h-6 w-6"*/}
                        {/*                 fill="none" viewBox="0 0 24 24">*/}
                        {/*                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"*/}
                        {/*                      d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>*/}
                        {/*            </svg>*/}
                        {/*            <span>Not Found Results</span>*/}
                        {/*        </div>*/}
                        {/*    </div>*/}
                        {/*}*/}
                    </form>
                </div>
            </div>
        </>
    )
}
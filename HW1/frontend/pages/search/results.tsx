import React, {useEffect, useRef, useState} from "react";
import Image from "next/image";

import img from "./imageWeather.png";
import {useRouter} from "next/router";

export default function Results() {
    const router = useRouter();
    const { country, state, city } = router.query;
    const [weatherData, setWeatherData] = useState([]);

    useEffect(() => {
        console.log(router.query)
        console.log(country, state, city)
            fetch("http://localhost:8080/api/v1/" + country + "/" + state + "/" + city + "/weather")
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    setWeatherData(data);
                })
                .catch(err => console.log(err));
    }, [])

    return (
        <div className="pt-20 h-screen flex justify-between">
            { <div className="card w-96 h-fit bg-base-100 shadow-xl image-full">
                <figure className="blur-sm">
                    <Image src={img} alt="Background" />
                </figure>
                <div className="card-body">
                    <h2 className="card-title">Weather</h2>
                    <div className="grid grid-cols-2">
                        <p>City</p>
                        <p>{weatherData["city"]}</p>
                        <p>Country</p>
                        <p>{weatherData["country"]}</p>
                        <p>State</p>
                        <p>{weatherData["state"]}</p>
                        <p>Date</p>
                        <p>{weatherData["date"]}</p>
                    </div>
                </div>
            </div>}
        </div>
    );
}
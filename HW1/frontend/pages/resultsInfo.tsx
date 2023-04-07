import { useRouter } from 'next/router';
import {useEffect, useState} from "react";

export default function ResultsInfo() {
    const router = useRouter();
    const { country, state, city, pollution, weather } = router.query;
    const [pollutionData, setPollutionData] = useState([]);
    const [weatherData, setWeatherData] = useState([]);

    useEffect(() => {
        if (pollution === "on") {
            fetch("http://localhost:8080/api/v1/" + country + "/" + state + "/" + city + "/pollution")
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    setPollutionData(data);
                })
                .catch(err => console.log(err));
            }

        if (weather === "on") {
            fetch("http://localhost:8080/api/v1/" + country + "/" + state + "/" + city + "/weather")
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    setWeatherData(data);
                })
                .catch(err => console.log(err));
        }
    }, [country, state, city, pollution, weather])

    return (
        <>
            <div className="card p-4 bg-gray-200 md:row-span-4">
                <div className="card-body">
                    <h2 className="text-lg font-bold mb-2"> CITY DATA</h2>
                    <p className="text-2xl font-bold">$120,000</p>
                </div>
            </div>

            {pollution === "on" && (
                <div className="flex flex-col">
                        <h1 className="text-5xl font-bold text-primary my-8 text-center">Pollution</h1>
                    <div className="flex-1 overflow-y-auto p-4">
                        <div className="h-3/4 w-full grid grid-rows-4 grid-cols-6 sm:grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-4">
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">Air Quality Index US</h2>
                                    <p className="text-2xl font-bold">10</p>
                                </div>
                            </div>
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">Mainus</h2>
                                    <p className="text-2xl font-bold">25</p>
                                </div>
                            </div>
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">Air Quality Index CN</h2>
                                    <p className="text-2xl font-bold">10</p>

                                </div>
                            </div>
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">maincn</h2>
                                    <p className="text-2xl font-bold">500</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            )}
            {weather === "on" && (
                <div className="flex flex-col">
                    <h1 className="text-5xl font-bold text-primary my-8 text-center">Weather</h1>
                    <div className="flex-1 overflow-y-auto p-4">
                        <div className="h-3/4 w-full grid grid-rows-4 grid-cols-6 sm:grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-4">
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">Temperature</h2>
                                    <p className="text-2xl font-bold">25</p>
                                </div>
                            </div>
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">Pressure</h2>
                                    <p className="text-2xl font-bold">10</p>

                                </div>
                            </div>
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">Humidity</h2>
                                    <p className="text-2xl font-bold">500</p>
                                </div>
                            </div>
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">Wind Speed</h2>
                                    <p className="text-2xl font-bold">25</p>
                                </div>
                            </div>
                            <div className="card p-2 bg-gray-200 md:row-span-2">
                                <div className="card-body">
                                    <h2 className="text-lg font-bold mb-2">Wind Direction</h2>
                                    <p className="text-2xl font-bold">10</p>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
}

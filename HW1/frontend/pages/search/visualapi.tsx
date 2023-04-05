import Link from "next/link";
import React, {useEffect, useRef, useState} from "react";

export default function VisualAPI() {
    const [apiError, setApiError] = useState(false);

    const [allCountry, setAllCountry] = useState([]);
    const [allState, setAllState] = useState([]);
    const [allCity, setAllCity] = useState([]);

    const [country, setCountry] = useState("");
    const [state, setState] = useState("");
    const [city, setCity] = useState("");

    const stateRef = useRef(null);
    const cityRef = useRef(null);

    useEffect(() => {
        fetch("http://localhost:8080/api/v1/countries")
            .then(res => res.json())
            .then(data => {
                setAllCountry(data);
            })
            .catch(err => setApiError(true));
    }, []);

    useEffect(() => {
        fetch("http://localhost:8080/api/v1/" + country + "/states")
            .then(res => res.json())
            .then(data => {
                setAllState(data);
            })
            .catch(err => setApiError(true));

        stateRef.current.selectedIndex = 0;
        setState("");
    }, [country]);

    useEffect(() => {
        fetch("http://localhost:8080/api/v1/" + country + "/" + state + "/cities")
            .then(res => res.json())
            .then(data => {
                setAllCity(data);
            })
            .catch(err => console.log(err));

        cityRef.current.selectedIndex = 0;
        setCity("");
    }, [state, country]);

    return (
        <>
            <div className="p-6 h-screen">
                <div className="min-h-screen bg-base-100 pt-20">
                    <form action="/search/results" method="GET" className="flex flex-col items-center">
                        <h1 className="text-5xl font-bold text-primary my-8">Weather Search</h1>
                        <select name="country" className="select select-info w-1/2 max-w-md mb-2" onChange={e => setCountry(e.target.value)}>
                            <option disabled selected>Select Country</option>
                            {allCountry.map((val, i) =>
                                <option value={val} key={i}>{val}</option>
                            )}
                        </select>

                        <select disabled={country === ""} ref={stateRef} name="state" className="select select-info w-1/2 max-w-md mb-2" onChange={e => setState(e.target.value)}>
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

                        <button className="btn btn-outline btn-success">Submit</button>

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
                    </form>
                </div>
            </div>
        </>
    )
}
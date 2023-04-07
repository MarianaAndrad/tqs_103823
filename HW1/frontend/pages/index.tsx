import Image from "next/image";
import Link from "next/link";

import img from "@/public/homeimage.jpg"

export default function Home() {
  return (
    <>
        <div className="min-h-screen bg-base-100">
            <div className="container mx-auto py-20">
                <div className="flex flex-col items-center">
                    <h1 className="text-5xl font-bold text-center mb-8">Air Quality and Weather Search</h1>
                    <Image className="w-64 h-64 rounded-full shadow-lg mb-8" src={img} alt="Example Image" />
                    <div className="grid grid-cols-2 gap-8">
                        <div className="card shadow-lg">
                            <div className="card-body">
                                <h2 className="card-title text-xl font-bold mb-4">Air Quality Search</h2>
                                <p className="card-text text-base mb-4">Search for real-time air quality information for a specific location by providing the city and country.</p>
                                <Link href="/search/openweather" className="btn btn-primary">Search</Link>
                            </div>
                        </div>
                        <div className="card shadow-lg">
                            <div className="card-body">
                                <h2 className="card-title text-xl font-bold mb-4">Weather Search</h2>
                                <p className="card-text text-base mb-4">Search for real-time weather information for a specific location by providing the city, state and country.</p>
                                <Link href="/search/visualapi" className="btn btn-primary">Search</Link>
                            </div>
                        </div>
                    </div>

                    <div className="grid grid-cols-1 gap-8">
                        <div className="card shadow-lg">
                            <div className="card-body">
                                <h2 className="card-title text-xl font-bold mb-4">Statistic Data</h2>
                                <p className="card-text text-base mb-4">View the statistic data for the APIs and the cache.</p>
                                <div className={"flex flex-row gap-4"}>
                                    <Link href="/statistics/api" className="btn btn-primary">Api</Link>
                                    <Link href="/statistics/cache" className="btn btn-primary">Cache</Link>
                                    <Link href="/statistics/controller" className="btn btn-primary">Controller</Link>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </>
  )
}

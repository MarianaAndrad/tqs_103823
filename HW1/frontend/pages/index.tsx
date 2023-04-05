import Image from "next/image";

import img from "@/public/homeimage.jpg"

export default function Home() {
  return (
    <>
        <div className="min-h-screen bg-base-100">
            <div className="container mx-auto py-20">
                <div className="flex flex-col items-center">
                    <h1 className="text-5xl font-bold text-center mb-8">Air Quality</h1>
                    <Image className="w-64 h-64 rounded-full shadow-lg mb-8" src={img} alt="Example Image" />
                    <p className="text-xl text-center mb-8">Get real-time air quality information for your location.</p>
                    <button className="btn btn-primary">Get Started</button>
                </div>
            </div>
        </div>
    </>
  )
}

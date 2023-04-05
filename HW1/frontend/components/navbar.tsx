import Link from "next/link";

export default function Navbar() {
    return (
        <div className="navbar absolute top-0 bg-transparent">
            <div className="navbar-start">
                <div className="dropdown">
                    <label tabIndex={0} className="btn btn-ghost btn-circle">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h7" /></svg>
                    </label>
                    <ul tabIndex={0} className="menu menu-compact dropdown-content mt-3 p-2 shadow bg-base-100 rounded-box w-52">
                        <li>
                            <Link href="/" className="Flex gap-4" > <span className="flex-1"> Air Quality </span> </Link>
                        </li>
                        <li></li>
                        <li className="menu-title">
                            <span>Visual API</span>
                        </li>
                        <li>
                            <Link href="/search/visualapi" className="Flex gap-4" > <span className="flex-1"> Weather Search </span> </Link>
                        </li>

                        <li></li>
                        <li className="menu-title">
                            <span>Open Weather</span>
                        </li>
                        <li>
                            <Link href="/search/openweather" className="Flex gap-4" > <span className="flex-1"> Air Quality Search </span> </Link>
                        </li>
                        <li></li>

                        <li className="menu-title">
                            <span>Statistics</span>
                        </li>
                        <li>
                            <Link href="/statistics/controller" className="Flex gap-4" > <span className="flex-1"> Controller </span> </Link>
                        </li>
                        <li>
                            <Link href="/statistics/cache" className="Flex gap-4" > <span className="flex-1"> Cache </span> </Link>
                        </li>
                        <li>
                            <Link href="/statistics/api" className="Flex gap-4" > <span className="flex-1"> Apis </span> </Link>
                        </li>
                    </ul>
                </div>
            </div>
            <div className="navbar-center">
                <a className="btn btn-ghost normal-case text-xl">Air Quality</a>
            </div>
            <div className="navbar-end">
            </div>
        </div>
    );
}
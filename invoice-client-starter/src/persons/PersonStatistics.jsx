import React, { useEffect, useState } from "react";
import { apiGet } from "../utils/api";

const PersonStatistics = () => {

    // State to hold statistics data fetched from the API
    const [statistics, setStatistics] = useState([]);

    // useEffect to load statistics when component mounts
    useEffect(() => {
        // Make a GET request to fetch person statistics
        apiGet("/api/persons/statistics")
            .then((data) => {
                // Log received data to confirm the response structure
                console.log("Received statistics:", data);      // makes sure it's an array
                setStatistics(data);
            })
            .catch((err) => {
                // Log any error that occurs during the API call
                console.error("Chyba při načítání statistik:", err);
            });
    }, []); // Empty dependency array means this runs only once on mount

    // If statistics are not yet loaded (empty array), shows loading message
    if (statistics.length === 0) return <p>Načítání statistik...</p>

    // Render the statistics in a table format
    return (
        <div>
            <h1>Výpis osobních statistik</h1>
            <table className="table table-bordered">
                <thead>
                    <tr>
                        <th>ID společnosti</th>
                        <th>Název společnosti</th>
                        <th>Fakturované příjmy</th>
                    </tr>
                </thead>
                <tbody>
                    {statistics.map(({ personId, personName, revenue }) => (
                        <tr key={personId}>
                            <td>{personId}</td>
                            <td>{personName}</td>
                            <td>{new Intl.NumberFormat("cs-CZ").format(revenue)} Kč</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default PersonStatistics;
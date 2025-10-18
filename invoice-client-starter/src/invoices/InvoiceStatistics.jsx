import React, { useEffect, useState } from "react";
import { apiGet } from "../utils/api";

const InvoiceStatistics = () => {
    
    // State to store statistics data fetched from the API
    const [statistics, setStatistics] = useState(null);
    
    // State to track if there was an error during fetch
    const [error, setError] = useState(null);

    // useEffect runs once after component mounts to fetch statistics
    useEffect(() => {
        apiGet("/api/statistics")                   // call the API endpoint to get statistics data
            .then((data) => setStatistics(data))    // on success, save data to state
            .catch((err) => {                       // on failure, log error and set error state
                console.error("Chyba při načítání statistik:", err);
                setError("Nepodařilo se načíst statistiky.");
            });
    }, []);     // empty dependency array means this runs only once on mount

    // If there was an error fetching data, show an error message
    if (error) return <p className="text-danger">{error}</p>;
    
    // Whiel waiting for data, show a loading message
    if (!statistics) return <p>Načítání statistik...</p>;

    // Once data is loaded, render the statistics in a table
    return (
        <div>
            <h1>Výpis obecných statistik</h1>
            <table className="table table-bordered">
                <tbody>
                    <tr>
                        <th>Součet cen za aktuální rok</th>
                        <td>{new Intl.NumberFormat('cs-CZ').format(statistics.currentYearSum)} Kč</td>
                    </tr>
                    <tr>
                        <th>Součet cen za všechny roky</th>
                        <td>{new Intl.NumberFormat('cs-CZ').format(statistics.allTimeSum)} Kč</td>
                    </tr>
                    <tr>
                        <th>Celkový počet faktur</th>
                        <td>{new Intl.NumberFormat('cs-CZ').format(statistics.invoicesCount)}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
};

export default InvoiceStatistics;
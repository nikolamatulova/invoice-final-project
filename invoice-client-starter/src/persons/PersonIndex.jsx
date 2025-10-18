/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */

import React, {useEffect, useState} from "react";

import {apiDelete, apiGet} from "../utils/api";

import PersonTable from "./PersonTable";

const PersonIndex = () => {
    // State to hold array of people fetched from the API
    const [persons, setPersons] = useState([]);

    // Function to delete a person by ID
    const deletePerson = async (id) => {

        const confirmDelete = window.confirm("Opravdu chcete vymazat tuto osobu?");
        
        if(!confirmDelete) return; //user clicked Cancel

        try {
            // Calls DELETE API endpoint for the given person ID
            await apiDelete("/api/persons/" + id);
        } catch (error) {
            // On error, logs to console and shows alert
            console.log(error.message);
            alert(error.message)
        }
        // Updates the state by removing the deleted person from the local array
        setPersons(persons.filter((item) => item._id !== id));
    };

    // useEffect hook to fetch the list of people on the first render
    useEffect(() => {
        // Calls GET API endpoint to retrieve all people
        apiGet("/api/persons").then((data) => setPersons(data));
    }, []);

    return (
        <div>
            <h1>Seznam osob</h1>
            <PersonTable
                deletePerson={deletePerson}
                items={persons}
                label="Počet osob:"
            />
        </div>
    );
};
export default PersonIndex;

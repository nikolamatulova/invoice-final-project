import React, { useEffect, useState } from "react";
import { apiGet } from "../utils/api";
import {useParams} from "react-router-dom"
import InvoiceTable from "./InvoiceTable";

const ReceivedInvoices = () => {
    
    // Extract identificationNumber from the URL parameters
    const { identificationNumber } = useParams();
    
    // State to hold the list of received invoices
    const [invoicesState, setInvoices] = useState([]);

    useEffect(() => {
        // Fetch received invoices for the given identificationNumber
        apiGet(`/api/identification/${identificationNumber}/received`)
        .then((data) => setInvoices(data));
    }, [identificationNumber]); // Re-run if the identificationNumber changes

    return (
        <div>
            <h1>Přijaté faktury</h1>
            <InvoiceTable items={invoicesState} label={`Počet faktur: ${invoicesState.length}`} />
        </div>
    );
};

export default ReceivedInvoices;
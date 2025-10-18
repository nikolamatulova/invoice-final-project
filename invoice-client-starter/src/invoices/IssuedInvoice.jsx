import React, { useEffect, useState } from "react";
import { apiGet } from "../utils/api";
import {useParams} from "react-router-dom"
import InvoiceTable from "./InvoiceTable";

const IssuedInvoices = () => {

    // Get the identificationNumber param from the URL
    const { identificationNumber } = useParams();
    
    // State to store list of issued invoices for the person
    const [invoicesState, setInvoices] = useState([]);

    useEffect(() => {
        // Fetch issued invoices for the given identificationNumber from API
        apiGet(`/api/identification/${identificationNumber}/issued`)
        .then((data) => setInvoices(data));     // Store the fetched invoices in state
            
    }, [identificationNumber]); // Re-run when identificationNumber changes

    return (
        <div>
            <h1>Vydané faktury</h1>
            <InvoiceTable items={invoicesState} label={`Počet faktur: ${invoicesState.length}`} />
        </div>
    );
};

export default IssuedInvoices;
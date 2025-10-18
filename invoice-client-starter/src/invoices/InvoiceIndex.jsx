import React, { useEffect, useState } from "react";
import InvoiceFilter from "./InvoiceFilter";
import InvoiceTable from "./InvoiceTable";
import { apiGet, apiDelete } from "../utils/api";

/**
 * InvoiceIndex component manages invoice list and filtering
 * It loads people for filter dropdowns, fetches invoices from the API,
 * an allows filtering and deleting of invoices
 */
const InvoiceIndex = () => {
    // State holding list of people (buyers and sellers)
    const [peopleList, setPeopleList] = useState([]);

    // State holding current invoices displayed
    const [invoiceList, setInvoiceList] = useState([]);

    // State holding current filter criteria
    const [filter, setFilter] = useState({
        seller: "",
        buyer: "",
        product: "",
        minPrice: "",
        maxPrice: "",
        limit: 10,
    });

    useEffect(() => {
        // Fetch people for sellers and buyers dropdown, convert _id to string
        apiGet("/api/persons").then((data) => {
            const peopleWithStringIds = data.map((person) => ({
                ...person,
                _id: person._id.toString(),
            }));
            setPeopleList(peopleWithStringIds);
        });

        // Fetch initial invoices
        fetchInvoices(filter);
    }, []);

    const fetchInvoices = async (params) => {                       //fetches invoices from the API using the current filter parameters
        const data = await apiGet("/api/invoices", params);
        setInvoiceList(data);                                       //after getting data, updates the invoiceList state
    };

    const handleChange = (e) => {
        const { name, value } = e.target;

        let parsedValue = value;

        //if the fields are either seller or buyer and not empty, we convert it to a number
        if ((name === "seller" || name === "buyer") && value !== "") {
            parsedValue = Number(value);
        }

        setFilter((prev) => ({
            ...prev,
            [name]: parsedValue,
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();         //prevents the page from refreshing
        fetchInvoices(filter);      //fetching invoices with the current filter
    };

    //deletes an invoice by ID from the API 
    const deleteInvoice = async (id) => {

        const confirmDelete = window.confirm("Opravdu chcete vymazat tuto fakturu?");

        if (!confirmDelete) return; //user clicked Cancel

        try {
            await apiDelete(`/api/invoices/${id}`);
            setInvoiceList((prev) => prev.filter((inv) => inv._id !== id));
        } catch (error) {
            console.error("Chyba při mazání faktury:", error);
            alert("Fakturu se nepodařilo smazat.");
        }
    };

    return (
        <div>
            <h1>Seznam faktur</h1>
            <hr />
            <InvoiceFilter                  //InvoiceFilter component with filter info and handlers
                peopleList={peopleList}
                filter={filter}
                handleChange={handleChange}
                handleSubmit={handleSubmit}
                confirm="Filtrovat faktury"
            />
            <hr />
            <InvoiceTable                   //InvoiceTable component with the list of invoices and delete function
                items={invoiceList}
                deleteInvoice={deleteInvoice}
                label={`Počet faktur: ${invoiceList.length}`}
            />
        </div>
    );
};

export default InvoiceIndex;
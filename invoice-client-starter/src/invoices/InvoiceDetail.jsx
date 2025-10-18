import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiGet } from "../utils/api";

/**
 * InvoiceDetail component fetches and displays detailed information
 * about a single invoice identified by the URL parameter `id`
 */
const InvoiceDetail = () => {

    // Extract invoice ID from URL parameters
    const { id } = useParams();

    // State to store invoice details
    const [invoice, setInvoice] = useState({});

    // Fetch invoice details when component mounts or `id` changes
    useEffect(() => {
        apiGet("/api/invoices/" + id)
            .then((data) => setInvoice(data))
            .catch(console.error);
    }, [id]);

    // Format dates for display, fallback to empty string if missing
    const issuedDate = invoice.issued ? new Date(invoice.issued).toLocaleDateString() : "";
    const dueDate = invoice.dueDate ? new Date(invoice.dueDate).toLocaleDateString() : "";

    return (
        <>
            <div>
                <h1>Detail faktury</h1>
                <hr />
                <h3>Faktura č. {invoice.invoiceNumber}</h3>
                <p>
                    <strong>Prodávající:</strong>
                    <br />
                    {invoice.seller?.name}
                </p>
                <p>
                    <strong>Kupující:</strong>
                    <br />
                    {invoice.buyer?.name}
                </p>
                <p>
                    <strong>Datum vystavení:</strong>
                    <br />
                    {issuedDate}
                </p>
                <p>
                    <strong>Datum splatnosti</strong>
                    <br />
                    {dueDate}
                </p>
                <p>
                    <strong>Produkt:</strong>
                    <br />
                    {invoice.product}
                </p>
                <p>
                    <strong>Cena:</strong>
                    <br />
                    {invoice.price} Kč
                </p>
                <p>
                    <strong>DPH:</strong>
                    <br />
                    {invoice.vat} %
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br />
                    {invoice.note}
                </p>
            </div>
        </>
    );
};

export default InvoiceDetail;
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";

import FlashMessage from "../components/FlashMessage";
import InputField from "../components/InputField";
import PersonForm from "../persons/PersonForm";
import InputSelect from "../components/InputSelect";

const InvoiceForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();


    // State hooks for each form field
    const [invoiceNumberState, setInvoiceNumber] = useState("");
    const [sellerState, setSeller] = useState("");
    const [buyerState, setBuyer] = useState("");
    const [issuedState, setIssued] = useState("");
    const [dueDateState, setDueDate] = useState("");
    const [productState, setProduct] = useState("");
    const [priceState, setPrice] = useState("");
    const [vatState, setVat] = useState("");
    const [noteState, setNote] = useState("");

    // List of people for seller/buyer dropdowns
    const [personsState, setPersons] = useState([]);
    
    // Status flags for form submission
    const [sent, setSent] = useState(false);                // creating a new piece of state called sent - tracks whether the form has been submitted
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState("");



    // If editing, fetch invoice data by id and populate states
    useEffect(() => {
        apiGet("/api/persons").then((data) => {
            console.log("persons:", data);
            setPersons(data);
        });
    }, []);

    useEffect(() => {
        if (id) {
            apiGet("/api/invoices/" + id).then((data) => {
                setInvoiceNumber(data.invoiceNumber);
                setSeller(data.seller?._id || "");
                setBuyer(data.buyer?._id || "");
                setIssued(data.issued?.substring(0, 10) || "");
                setDueDate(data.dueDate?.substring(0, 10) || "");
                setProduct(data.product);
                setPrice(data.price);
                setVat(data.vat);
                setNote(data.note || "");
            });
        }
    }, [id]);


    // Handle any input change and update corresponding state
    const handleChange = (e) => {
        const { name, value } = e.target;

        switch (name) {
            case "invoiceNumber":
                setInvoiceNumber(value)
                break;
            case "seller":
                setSeller(value)
                break;
            case "buyer": 
                setBuyer(value)
                break;
            case "issued":
                setIssued(value)
                break;
            case "dueDate":
                setDueDate(value)
                break;
            case "product":
                setProduct(value)
                break;
            case "price":
                setPrice(value)
                break;
            case "vat":
                setVat(value)
                break;
            case "note":
                setNote(value)
                break;
            default:
                // Optionally handle unknown fields
                console.warn(`Unhandled input name: ${name}`);                                    
        }
    };

    // Submit form data: create or update invoice via API
    const handleSubmit = (e) => {
        e.preventDefault();

        const body = {
            invoiceNumber: parseInt(invoiceNumberState),
            seller: { _id: parseInt(sellerState) },
            buyer: { _id: parseInt(buyerState) },
            issued: issuedState,
            dueDate: dueDateState,
            product: productState,
            price: parseInt(priceState),
            vat: parseInt(vatState),
            note: noteState,
        };

        (id
            ? apiPut("/api/invoices/" + id, body)
            : apiPost("/api/invoices/", body)
        )
            .then((data) => {
                console.log("success", data);
                setSent(true);
                setSuccess(true);
                navigate("/invoices");
            })
            .catch((error) => {
                console.log(error.message);
                setError(error.message);
                setSent(true);
                setSuccess(false);
            })
    }

    return (
        <div>
            <h1> {id ? "Upravit" : "Vytvořit"} fakturu </h1>
            <hr />
            {errorState ? (
                <div className="alert alert-danger">{errorState}</div>
            ) : null}
            {sent && success ?(
                <FlashMessage
                    theme={successState ? "success" : ""}
                    text={successState ? "Uložení faktury proběhlo úspěšně." : ""}
                />
            ) : null}

            <form onSubmit={handleSubmit}>

                <InputField
                    required={true}
                    type="text"
                    name="invoiceNumber"
                    min="3"
                    label="Číslo faktury"
                    prompt="Zadejte číslo faktury"
                    value={invoiceNumberState}
                    handleChange={handleChange}
                />

                <InputSelect
                    required={true}
                    name="seller"
                    label="Prodávající"
                    prompt="Vyberte prodávajícího"
                    items={personsState}
                    value={sellerState}
                    handleChange={handleChange}
                />

                <InputSelect
                    required={true}
                    name="buyer"
                    label="Kupující"
                    prompt="Vyberte kupujícího"
                    items={personsState}
                    value={buyerState}
                    handleChange={handleChange}
                />

                <InputField
                    required={true}
                    type="date"
                    name="issued"
                    min="3"
                    label="Datum vystavení"
                    prompt="Zadejte datum vystavení"
                    value={issuedState}
                    handleChange={handleChange}
                />

                <InputField
                    required={true}
                    type="date"
                    name="dueDate"
                    min="3"
                    label="datum splatnosti"
                    prompt="Zadejte datum splatnosti"
                    value={dueDateState}
                    handleChange={handleChange}
                />

                <InputField
                    required={true}
                    type="text"
                    name="product"
                    min="3"
                    label="Produkt"
                    prompt="Zadejte, o jaký produkt se jednalo"
                    value={productState}
                    handleChange={handleChange}
                />

                <InputField
                    required={true}
                    type="text"
                    name="price"
                    minLength={1}
                    label="Cena"
                    prompt="Zadejte celkovou částku"
                    value={priceState}
                    handleChange={handleChange}
                />

                <InputField
                    required={true}
                    type="text"
                    name="vat"
                    minLength={1}
                    label="DPH"
                    prompt="Zadejte hodnotu DPH bez"
                    value={vatState}
                    handleChange={handleChange}
                />

                <InputField
                    required={true}
                    type="text"
                    name="note"
                    min="3"
                    label="Poznámka"
                    prompt="Zadejte poznámku"
                    value={noteState}
                    handleChange={handleChange}
                />

                <input type="submit" className="btn btn-primary" value="Uložit" />
            </form>

        </div>

    );

};

export default InvoiceForm;
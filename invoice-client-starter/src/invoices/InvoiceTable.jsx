import React from "react";
import { Link } from 'react-router-dom';

const InvoiceTable = ({ label, items, deleteInvoice }) => {
    return (
        <div>
            <p>
                {label}
            </p>

            <table className="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Číslo faktury</th>
                        <th>Prodávající</th>
                        <th>Kupující</th>
                        <th>Datum vystavení</th>
                        <th>Datum splatnosti</th>
                        <th>Produkt</th>
                        <th>Cena</th>
                        <th>Akce</th>
                    </tr>
                </thead>
                <tbody>
                    {items.map((item, index) => (
                        <tr key={index + 1}>
                            <td>{index + 1}</td>
                            <td>{item.invoiceNumber}</td>
                            <td>{item.seller.name}</td>
                            <td>{item.buyer.name}</td>
                            <td>{new Intl.DateTimeFormat('cs-CZ', { year: 'numeric', month: '2-digit', day: '2-digit' }).format(new Date(item.issued))}</td>
                            <td>{new Intl.DateTimeFormat('cs-CZ', { year: 'numeric', month: '2-digit', day: '2-digit' }).format(new Date(item.dueDate))}</td>
                            <td>{item.product}</td>
                            <td>{new Intl.NumberFormat('cs-CZ').format(item.price)} Kč</td>
                            <td>
                                <div className="btn-group gap-1">
                                    <Link
                                        to={"/invoices/show/" + item._id}
                                        className="btn btn-sm btn-info"
                                    >
                                        Zobrazit
                                    </Link>
                                    <Link
                                        to={"/invoices/edit/" + item._id}
                                        className="btn btn-sm btn-warning"
                                    >
                                        Upravit
                                    </Link>
                                    <button
                                        onClick={() => {
                                            console.log("Smazání faktury č.:", item._id);
                                            deleteInvoice(item._id);
                                        }}
                                        className="btn btn-sm btn-danger"
                                    >
                                        Odstranit
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="btn-group gap-1">
                <Link to={"/invoices/create"} className="btn btn-success">
                    Nová faktura
                </Link>
                <Link to={"/invoices/statistics"} className="btn btn-primary">
                    Obecné statistiky
                </Link>
            </div>
        </div>
    );
};

export default InvoiceTable;
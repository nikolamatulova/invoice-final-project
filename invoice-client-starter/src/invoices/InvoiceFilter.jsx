import React from "react";
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";


/**
 * InvoiceFilter component renders a form for filtering invoices
 * It receives current filter values, a list of people for dropdowns,
 * and handlers to manage changes and form submission
 */
const InvoiceFilter = (props) => {          // Receives props from its parent: filter, peopleList, handleChange, handleSubmit, confirm
  
// Calls the parent's handleChange when the user changes an input 
    const handleChange = (e) => {
    props.handleChange(e);
  };

// Prevents the default page reload and calls the parent's handleSubmit 
  const handleSubmit = (e) => {
    e.preventDefault();
    props.handleSubmit(e);
  };

  const filter = props.filter;

  return (
    <form onSubmit={handleSubmit}>
      <div className="row">
        <div className="col">
          <InputSelect
            name="seller"
            items={props.peopleList}
            handleChange={handleChange}
            label="Prodávající"
            prompt="nevybrán"
            value={filter.seller?.toString() || ""}     //if filter.buyer exists and is not null or undefined, then convert to String
          />
        </div>
        <div className="col">
          <InputSelect
            name="buyer"
            items={props.peopleList}
            handleChange={handleChange}
            label="Kupující"
            prompt="nevybrán"
            value={filter.buyer?.toString() || ""}
          />
        </div>
        <div className="col">
          <InputField
            type="text"
            name="product"
            handleChange={handleChange}
            label="Produkt"
            placeholder="Zadejte produkt"
            value={filter.product || ""}
          />
        </div>
      </div>

      <div className="row">
        <div className="col">
          <InputField
            type="number"
            name="minPrice"
            handleChange={handleChange}
            label="Minimální cena"
            placeholder="Minimální cena"
            value={filter.minPrice || ""}
          />
        </div>
        <div className="col">
          <InputField
            type="number"
            name="maxPrice"
            handleChange={handleChange}
            label="Maximální cena"
            placeholder="Maximální cena"
            value={filter.maxPrice || ""}
          />
        </div>
        <div className="col">
          <InputField
            type="number"
            name="limit"
            handleChange={handleChange}
            label="Limit výsledků"
            placeholder="Limit"
            value={filter.limit || ""}
          />
        </div>
      </div>

      <div className="row">
        <div className="col">
          <input
            type="submit"
            className="btn btn-secondary mt-2"
            value={props.confirm || "Filtrovat faktury"}
          />
        </div>
      </div>
    </form>
  );
};

export default InvoiceFilter;
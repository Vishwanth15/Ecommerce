import React from "react";

function ProductCard({ product }) {

  return (
    <div className="col-md-3 mb-4">

      <div className="card shadow-sm h-100">

        <img
          src="https://via.placeholder.com/300"
          className="card-img-top"
          alt="product"
        />

        <div className="card-body">

          <h5 className="card-title">
            {product.name}
          </h5>

          <p className="card-text text-muted">
            {product.description}
          </p>

          <h6 className="text-success">
            ₹{product.price}
          </h6>

          <button className="btn btn-primary w-100">
            Add to Cart
          </button>

        </div>

      </div>

    </div>
  );
}

export default ProductCard;
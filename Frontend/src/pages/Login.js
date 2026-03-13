import React from "react";

function Login() {

  return (
    <div className="container mt-5">

      <div className="row justify-content-center">

        <div className="col-md-4">

          <div className="card p-4 shadow">

            <h3 className="text-center mb-3">
              Login
            </h3>

            <input
              className="form-control mb-3"
              placeholder="Email"
            />

            <input
              type="password"
              className="form-control mb-3"
              placeholder="Password"
            />

            <button className="btn btn-dark w-100">
              Login
            </button>

          </div>

        </div>

      </div>

    </div>
  );
}

export default Login;
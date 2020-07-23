import React from 'react'

function ProBanner() {
    const toggleProBanner = () => {};

    return (
      <div className="row proBanner">
        <div className="col-12">
          <span className="d-flex align-items-center purchase-popup">
            <p>Like what you see? Check out our premium version for more.</p>
            <a
              href="https://github.com/BootstrapDash/StarAdmin-Free-React-Admin-Template"
              target="_blank"
              rel="noopener noreferrer"
              className="btn ml-auto download-button"
            >
              Download Free Version
            </a>
            <a
              href="http://www.bootstrapdash.com/demo/star-admin-pro-react/template/demo_1/preview/dashboard"
              rel="noopener noreferrer"
              target="_blank"
              className="btn purchase-button"
            >
              Upgrade To Pro
            </a>
            <i
              className="mdi mdi-close bannerClose"
              onClick={toggleProBanner}
            ></i>
          </span>
        </div>
      </div>
    );
}

export default ProBanner

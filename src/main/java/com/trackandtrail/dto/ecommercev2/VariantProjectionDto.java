package com.trackandtrail.dto.ecommercev2;

import java.util.ArrayList;
import java.util.List;

import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;

public interface VariantProjectionDto {
	
	 Long getid();

	 String getvariantName();

	 String getvariantCode();

	 Long getproductId();

	 String getprice();

	 String getproductUrl();

	 Integer getquantity();

	 Double getdiscountPercentage();

	 Double getdiscountAmount();

	 String getimage1();

	 String getimage2();

	 String getimage3();

	 String getimage4();

	 String getimage5();

	 String getvideoUrl();

	 String getdescription();

	 Integer getavgRating();
	
	Long getproductTypeTermId();

	Long getcolorTermId();

	Long getsizeTermId();

	 


}

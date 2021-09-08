package com.trackandtrail.mapper;

import com.trackandtrail.dto.BikeRentalProductDto;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;

public class BikeRentalProductMapper {
	public static void toBikeRentalProduct(BikeRentalProductDto s, BikeRentalProduct d) {
		if (s == null) {
			return;
		}

		if (s.getProductSku() != null)
			d.setProductSku(s.getProductSku());

		if (s.getName() != null)
			d.setName(s.getName());

		if (s.getThumbnail() != null)
			d.setThumbnail(s.getThumbnail());

		if (s.getType() != null)
			d.setType(s.getType());


		if (String.valueOf(s.getPrice()) != null)
			d.setPrice(s.getPrice());


		

		if (s.getWebsiteLink() != null)
			d.setWebsiteLink(s.getWebsiteLink());

		if (s.getColor() != null)
			d.setColor(s.getColor());

		if (String.valueOf(s.getSize()) != null)
			d.setSize(s.getSize());

		if (s.getAbout() != null)
			d.setAbout(s.getAbout());

		if (s.getGears() != null)
			d.setGears(s.getGears());

		if (s.getBrakesFront() != null)
			d.setBrakesFront(s.getBrakesFront());

		if (s.getHandleBar() != null)
			d.setHandleBar(s.getHandleBar());

		if (s.getRoadType() != null)
			d.setRoadType(s.getRoadType());


		if (s.getBrakeRear() != null)
			d.setBrakeRear(s.getBrakeRear());

		if (s.getStem() != null)
			d.setStem(s.getStem());

		if (s.getFrame() != null)
			d.setFrame(s.getFrame());


		if (s.getFieldStand() != null)
			d.setFieldStand(s.getFieldStand());


		if (s.getHeadSet() != null)
			d.setHeadSet(s.getHeadSet());

		if (s.getCrank() != null)
			d.setCrank(s.getCrank());

		if (s.getSeatClamps() != null)
			d.setSeatClamps(s.getSeatClamps());

		if (s.getBottomBracket() != null)
			d.setBottomBracket(s.getBottomBracket());


		if (s.getSpokes() != null)
			d.setSpokes(s.getSpokes());


		if (s.getSaddle() != null)
			d.setSaddle(s.getSaddle());


		if (s.getRearDerailleur() != null)
			d.setRearDerailleur(s.getRearDerailleur());


		if (s.getTires() != null)
			d.setTires(s.getTires());

		if (s.getSeatPost() != null)
			d.setSeatPost(s.getSeatPost());

		if (s.getRims() != null)
			d.setRims(s.getRims());

		if (s.getPedals() != null)
			d.setPedals(s.getPedals());

		if (s.getFork() != null)
			d.setFork(s.getFork());

		if(s.getChainCover()!=null)
			d.setChainCover(s.getChainCover());

		if (s.getBrakeLevers() != null)
			d.setBrakeLevers(s.getBrakeLevers());
		
		
		if (s.getBikeBrand() != null)
			d.setBikeBrand(s.getBikeBrand());
		
		if (s.getBikeModel() != null)
			d.setBikeModel(s.getBikeModel());

}
}




































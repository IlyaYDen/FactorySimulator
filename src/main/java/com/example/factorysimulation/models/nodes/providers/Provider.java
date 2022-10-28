package com.example.factorysimulation.models.nodes.providers;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.details.DetailsEnum;
import com.example.factorysimulation.models.nodes.ModelNode;
import com.example.factorysimulation.models.nodes.stock.DetailsStock;

public interface Provider {
   //Runnable start(DetailsStock detailsStock) throws InterruptedException;

   DetailsEnum getDetailType();

   Object getLock7();
   
   boolean hasDetails();

   void setCons(DetailsStock detailsStock);

   String getName();

   void askNewDetails();

   Detail getDetail() throws InterruptedException;

    void setDetails(DetailsEnum d);

   void setSpeed(int parseInt);

   boolean hasSpeed();

   int getSpeed();
   //public void setDetailOut(DetailsEnum d);
}

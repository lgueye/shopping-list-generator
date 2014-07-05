package com.swissquote.battledev2014.shoppinglistgenerator.domain;

/**
 * (c) Swissquote 7/3/14
 *
 * @author lorenzo
 */
public enum  UnitEnum {

    //reference is g for solid, cl for liquids

    g(1.0),dl(10.0),kg(1000.0),l(100.0),cl(1.0),ml(0.1),unit(1);

    public double getRatioToBase() {
        return ratioToBase;
    }

    private double ratioToBase;

     UnitEnum(double ratioToBase){
        this.ratioToBase=ratioToBase;
    }

}

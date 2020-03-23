package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.RightOpenInterval;

public enum  MoonModel implements CelestialObjectModel<Moon> {
    MOON;

    private final static double l0= Angle.ofDeg(91.929336),P0=Angle.ofDeg(130.143076),N0=Angle.ofDeg(291.682547),i=Angle.ofDeg(5.145396),e=0.0549;


    @Override
    public Moon at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {

        //Orbital Longitude
        Sun sun=SunModel.SUN.at(daysSinceJ2010,eclipticToEquatorialConversion);
        double l=Angle.normalizePositive(Angle.ofDeg(13.1763966)*daysSinceJ2010+l0);
        double Mm=Angle.normalizePositive(l-Angle.ofDeg(0.1114041)*daysSinceJ2010-P0);
        double Ev=Angle.ofDeg(1.2739)*Math.sin(2* (l-sun.eclipticPos().lon()) - Mm );
        double Ae=Angle.ofDeg(0.1858)*Math.sin(sun.meanAnomaly());
        double A3=Angle.ofDeg(0.37)*Math.sin(sun.meanAnomaly());
        double Mmbis=Mm+Ev-Ae-A3;
        double Ec=Angle.ofDeg(6.2886)*Math.sin(Mmbis);
        double A4=Angle.ofDeg(0.214)*Math.sin(2*Mmbis);
        double lbis=l+Ev+Ec-Ae+A4;
        double V=Angle.ofDeg(0.6583)*Math.sin(2*(lbis-sun.eclipticPos().lon()));
        double l2bis=lbis+V;

        //EclipticPosition

        double N=N0-Angle.ofDeg(0.0529539)*daysSinceJ2010;
        double Nbis=N-Angle.ofDeg(0.16)*Math.sin(sun.meanAnomaly());
        double lon=Math.atan2( Math.sin(l2bis-Nbis)*Math.cos(i),
                                Math.cos(l2bis-Nbis))+Nbis;
        double lat=Math.asin( Math.sin(l2bis-Nbis )*Math.sin(i) );

        EclipticCoordinates coordinates=EclipticCoordinates.of(Angle.normalizePositive(lon), RightOpenInterval.symmetric(Angle.TAU / 2).reduce(lat));

        //Phase

        double F=(1-Math.cos(l2bis-sun.eclipticPos().lon()))/2;

        //Angular Size

        double rho =(1-e*e) / (1+e*Math.cos(Mmbis+Ec));
        double theta= Angle.ofDeg(0.5181)/rho;

        return new Moon(eclipticToEquatorialConversion.apply(coordinates),(float) theta,0,(float)F);

    }
}

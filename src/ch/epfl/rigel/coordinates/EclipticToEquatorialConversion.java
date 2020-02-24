package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;

public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates, EquatorialCoordinates>
{

    private final double sinEpsylon,cosEpsylon,Epsylon;



    public EclipticToEquatorialConversion(ZonedDateTime when){
        double T=0;//TODO;

        Epsylon= Angle.ofArcsec(0.00181)*T*T*T-Angle.ofArcsec(0.0006)*T*T-Angle.ofArcsec(46.815)*T+Angle.ofDMS(23,26,21.45);
        sinEpsylon=Math.sin(Epsylon);
        cosEpsylon=Math.cos(Epsylon);
    }


    @Override
    public EquatorialCoordinates apply(EclipticCoordinates ec1) {
     double sinTheta=Math.sin(ec1.lon());

     double alpha=(sinTheta*cosEpsylon-Math.tan(ec1.lat())) / (Math.cos(ec1.lon()) );
     alpha=Math.atan2(alpha,1);
     double delta= Math.asin(Math.sin(ec1.lat())*cosEpsylon  + Math.cos(ec1.lat())*sinEpsylon*sinTheta );


     return EquatorialCoordinates.of(alpha,delta);
    }



    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}


package ro.disertatie.cleanbud.View.ViewModel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import ro.disertatie.cleanbud.View.Fragments.TripFilterFragment;
import ro.disertatie.cleanbud.databinding.TripFilterFragmentBinding;

public class TripsFilterViewModel {
    private TripFilterFragment tripFilterFragment;
    private TripFilterFragmentBinding tripFilterFragmentBinding;
    private static final String[] CITIES = new String[]{
            "Barcelona","Bucharest","Madrid","Paris","Nice","Bordeux"
    };

    public TripsFilterViewModel(TripFilterFragment tripFilterFragment, TripFilterFragmentBinding tripFilterFragmentBinding) {
        this.tripFilterFragment = tripFilterFragment;
        this.tripFilterFragmentBinding = tripFilterFragmentBinding;
    }

    public void initProgressPrice(){
        final int max = 45;
        final int min = 10;
        final int total = max - min;
        tripFilterFragmentBinding.fluidSlider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                return  Unit.INSTANCE;
            }
        });


        tripFilterFragmentBinding.fluidSlider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                return  Unit.INSTANCE;
            }
        });

        tripFilterFragmentBinding.fluidSlider.setPositionListener(pos -> {
            final String value = String.valueOf( (int)(min + total * pos) );
            tripFilterFragmentBinding.fluidSlider.setBubbleText(value);
            return Unit.INSTANCE;
        });

        tripFilterFragmentBinding.fluidSlider.setPosition(0.3f);
        tripFilterFragmentBinding.fluidSlider.setStartText(String.valueOf(min));
        tripFilterFragmentBinding.fluidSlider.setEndText(String.valueOf(max));
    }


}

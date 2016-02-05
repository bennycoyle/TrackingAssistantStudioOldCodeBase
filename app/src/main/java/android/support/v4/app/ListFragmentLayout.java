package android.support.v4.app;

import com.bc.navweightwatchers.R;
import android.view.View;

public class ListFragmentLayout {
	public static void setupIds(View view) {
		view.findViewById(R.id.flitem).setId(ListFragment.INTERNAL_EMPTY_ID);
        view.findViewById(R.id.flgrams).setId(ListFragment.INTERNAL_EMPTY_ID);
        view.findViewById(R.id.flpointsLayout).setId(ListFragment.INTERNAL_EMPTY_ID);
        view.findViewById(R.id.table_list_container_id).setId(ListFragment.INTERNAL_LIST_CONTAINER_ID);
	}
	public static void setupTreatIds(View view) {
		view.findViewById(R.id.treatItem).setId(ListFragment.INTERNAL_EMPTY_ID);
        view.findViewById(R.id.treatAmount).setId(ListFragment.INTERNAL_EMPTY_ID);
        view.findViewById(R.id.treat_list_container_id).setId(ListFragment.INTERNAL_LIST_CONTAINER_ID);
	}
	public static void setupCostaDrinksIds(View view) {
		view.findViewById(R.id.cdrink).setId(ListFragment.INTERNAL_EMPTY_ID);
		view.findViewById(R.id.cmilk).setId(ListFragment.INTERNAL_EMPTY_ID);
		view.findViewById(R.id.csmall).setId(ListFragment.INTERNAL_LIST_CONTAINER_ID);
		view.findViewById(R.id.cmed).setId(ListFragment.INTERNAL_EMPTY_ID);
		view.findViewById(R.id.clarge).setId(ListFragment.INTERNAL_EMPTY_ID);
	}
}

package com.example.tetris3;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.tetris3.databinding.ActivityConfigBindingImpl;
import com.example.tetris3.databinding.ActivityJogoBindingImpl;
import com.example.tetris3.databinding.ActivityMainBindingImpl;
import com.example.tetris3.databinding.ActivityResultadoBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCONFIG = 1;

  private static final int LAYOUT_ACTIVITYJOGO = 2;

  private static final int LAYOUT_ACTIVITYMAIN = 3;

  private static final int LAYOUT_ACTIVITYRESULTADO = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.tetris3.R.layout.activity_config, LAYOUT_ACTIVITYCONFIG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.tetris3.R.layout.activity_jogo, LAYOUT_ACTIVITYJOGO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.tetris3.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.tetris3.R.layout.activity_resultado, LAYOUT_ACTIVITYRESULTADO);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCONFIG: {
          if ("layout/activity_config_0".equals(tag)) {
            return new ActivityConfigBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_config is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYJOGO: {
          if ("layout/activity_jogo_0".equals(tag)) {
            return new ActivityJogoBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_jogo is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYRESULTADO: {
          if ("layout/activity_resultado_0".equals(tag)) {
            return new ActivityResultadoBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_resultado is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/activity_config_0", com.example.tetris3.R.layout.activity_config);
      sKeys.put("layout/activity_jogo_0", com.example.tetris3.R.layout.activity_jogo);
      sKeys.put("layout/activity_main_0", com.example.tetris3.R.layout.activity_main);
      sKeys.put("layout/activity_resultado_0", com.example.tetris3.R.layout.activity_resultado);
    }
  }
}

package com.example.tetris3.databinding;
import com.example.tetris3.R;
import com.example.tetris3.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityJogoBindingImpl extends ActivityJogoBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textViewConfig, 1);
        sViewsWithIds.put(R.id.textView, 2);
        sViewsWithIds.put(R.id.textViewPontosResultado, 3);
        sViewsWithIds.put(R.id.textViewProxima, 4);
        sViewsWithIds.put(R.id.imageButtonPause, 5);
        sViewsWithIds.put(R.id.gridboard, 6);
        sViewsWithIds.put(R.id.imageButtonGirarPeca, 7);
        sViewsWithIds.put(R.id.imageButtonDireita, 8);
        sViewsWithIds.put(R.id.imageButtonBaixo, 9);
        sViewsWithIds.put(R.id.imageButtonEsquerda, 10);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityJogoBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ActivityJogoBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.GridLayout) bindings[6]
            , (android.widget.ImageButton) bindings[9]
            , (android.widget.ImageButton) bindings[8]
            , (android.widget.ImageButton) bindings[10]
            , (android.widget.ImageButton) bindings[7]
            , (android.widget.ImageButton) bindings[5]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            );
        this.linearLayout.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}
package com.camc.factory.ui.feature.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camc.factory.data.model.entity.RecordCategory
import com.camc.factory.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _categoryList = mutableStateOf<List<RecordCategory>>(emptyList())
    val categoryList: MutableState<List<RecordCategory>> get() = _categoryList

    // Input fields
    private val _name = mutableStateOf("")
    var name: String
        get() = _name.value
        set(value) {
            _name.value = value
        }
    private val _description = mutableStateOf("")
    var description: String
        get() = _description.value
        set(value) {
            _description.value = value
        }

    private val _demandRecords = mutableStateOf(0)
    var demandRecords: Int
        get() = _demandRecords.value
        set(value) {
            _demandRecords.value = value
        }


    init {
        // Perform any necessary initialization here
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getCategorys().collect { categories ->
                _categoryList.value = categories
            }
        }
    }

    fun addCategory(recordCategory: RecordCategory) {
        viewModelScope.launch {
            categoryRepository.insertCategory(recordCategory)
        }
    }

    fun deleteCategory(recordCategory: RecordCategory) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(recordCategory)
        }
    }
    private var editCategory: RecordCategory? = null
    var onCategoryItemClick: ((index: Int, category: RecordCategory) -> Unit)? = null
    fun onCategoryItemClick(index: Int, category: RecordCategory) {
        // 处理项的点击事件
        // 可以使用传递的索引和分类信息执行特定的操作
        // 例如，更新当前选中的分类，显示详细信息等
        editCategory = category
    }

    fun getEditCategory(): RecordCategory? {
        return editCategory
    }


}

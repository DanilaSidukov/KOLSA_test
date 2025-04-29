package com.diphrogram.kolsa_test.common

import android.view.LayoutInflater
import android.view.ViewGroup

typealias BindingInflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

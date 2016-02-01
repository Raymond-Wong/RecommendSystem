#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Date    : 2016-02-01 11:53:42
# @Author  : RaymondWong (549425036@qq.com)
# @Link    : github/Raymond-Wong

import os
import sys
import numpy as np

import config.Const as Const
import utils.CommonUtil as CommonUtil
import handlers.DataHandler as DataHandler
import handlers.TrainHandler as TrainHandler

# 将src文件下的所有目录都导入系统变量中方便导入

def main():
  # 读取训练集中所有用户和物品的数量
  (userSet, itemSet) = DataHandler.getUsersAndItems(Const.RAW_TRAIN_DATA_PATH)
  CommonUtil.logger("Main", "DEBUG", "获取到 " + str(len(userSet)) + " 个用户和 " + str(len(itemSet)) + " 个item")
  # 读取用户对物品的态度矩阵(正确,目标)
  targetMatrix = DataHandler.getTargetMatrix(Const.TRAIN_DATA_PATH, userSet, itemSet)
  # 构造用户到隐空间的映射矩阵
  userMatrix = np.array([[0.1] * Const.FEATURES_AMOUNT] * len(userSet), dtype=np.float64)
  userMatrix.shape = (len(userSet), Const.FEATURES_AMOUNT)
  # 构造物品到隐空间的映射矩阵
  itemMatrix = np.array([[0.1] * Const.FEATURES_AMOUNT] * len(itemSet), dtype=np.float64)
  itemMatrix.shape = (len(itemSet), Const.FEATURES_AMOUNT)
  # 开始迭代用户矩阵和物品矩阵
  (userMatrix, itemMatrix) = TrainHandler.iterateTrain(targetMatrix, userMatrix, itemMatrix)

if __name__ == "__main__":
  startKey = CommonUtil.startProcess('Main', '主程序')
  main()
  CommonUtil.endProcess(startKey)

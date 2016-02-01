#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Date    : 2016-02-01 11:54:12
# @Author  : RaymondWong (549425036@qq.com)
# @Link    : github/Raymond-Wong

import os
import sys
import random
import time
sys.path.append("..")

import config.Const as Const

# 判断一个字符串是否为空
def isStringBlank(string):
  return (string is None) or (len(string) == 0) or (len(string.replace(" ", "").replace("\t", "").replace("\n", "").replace("\r", "")) == 0)

# 用于输出日志
def logger(clazz, tp, msg):
  sdf1 = "%y-%m-%d %H:%M:%S"
  sdf2 = "%H:%M:%S"
  if not (not Const.DEBUG and tp.lower() == "debug"):
    print "[" + clazz + "][" + tp + "][" + time.strftime(sdf2, time.localtime())+ "]" + "\t" + msg

# 记录不同进程开始和计数的数组
processRecord = {}

# 记录一个进程开始的时间,并输出log
# 返回一个key,进程结束时传入这个key即可调出相应的结束语
def startProcess(clazz, msg):
  key = str(random.random())
  logger(clazz, 'INFO', "开始 " + msg)
  processRecord[key] = (clazz, msg, time.clock())
  return key
def endProcess(key):
  if processRecord[key] is None:
    return
  logger(processRecord[key][0], 'INFO', "结束 " + processRecord[key][1] + ", 共耗时 " + str(time.clock() - processRecord[key][2]) + " 秒")
  processRecord[key] = None

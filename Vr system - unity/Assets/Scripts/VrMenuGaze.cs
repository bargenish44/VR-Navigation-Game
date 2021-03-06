﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Events;
using System;
namespace Presentation
{
    public class VrMenuGaze : MonoBehaviour
    {
        public Image imgGaze;
        public UnityEvent GVRClick;
        [SerializeField] private float totalTime = 2;
        private bool gvrStatus = false;
        private float gvrTimer;

        private void Start()
        {
            imgGaze.fillAmount = 0;
        }
        void Update()
        {
            if (gvrStatus)
            {
                gvrTimer += Time.deltaTime;
                imgGaze.fillAmount = gvrTimer / totalTime;
            }
            if (gvrTimer >= totalTime)
            {
                GVRClick.Invoke();
                GVROff();
            }
        }

        public void GVRon()
        {
            gvrStatus = true;
        }
        public void GVROff()
        {
            gvrStatus = false;
            gvrTimer = 0;
            try
            {
                imgGaze.fillAmount = 0;
            }
            catch (Exception e) { }
        }
    }
};
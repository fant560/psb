import layoutparser as lp

model = lp.Detectron2LayoutModel('lp://PrimaLayout/mask_rcnn_R_50_FPN_3x/config', 
                                    extra_config=["MODEL.ROI_HEADS.SCORE_THRESH_TEST", 0.4],
                                    label_map={0: "Text", 1: "Title", 2: "List", 3:"Table", 4:"Figure"})
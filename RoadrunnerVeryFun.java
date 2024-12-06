package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Roadrunner is very fun")
public class RoadrunnerVeryFun extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0)); // Initial pose
        Servo yslide = hardwareMap.get(Servo.class, "Yslide");
        Servo yslide2 = hardwareMap.get(Servo.class, "Yslide2");
        Servo yrotate = hardwareMap.get(Servo.class, "Yrotate");
        Servo yrotate2 = hardwareMap.get(Servo.class, "Yrotate2");
        Servo Rrotate = hardwareMap.get(Servo.class, "Rrotate");
        Servo gripper = hardwareMap.get(Servo.class, "Gripper");
        waitForStart();

        // Move 20 inches to the right (positive X direction)
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0)) // Starting pose
                        .strafeTo(new Vector2d(0, -5))
                        .stopAndAdd(new Grab(yrotate,0.15))
                        .stopAndAdd(new Grab(yrotate2,0.85))
                        .waitSeconds(0.2)

                        .build()
        );
    }
    public class Grab implements Action {
        Servo servo;
        double position;
        public Grab(Servo s , double p){
            this.servo = s;
            this.position = p;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket){
            servo.setPosition(position);
            return false;
        }
    }
}

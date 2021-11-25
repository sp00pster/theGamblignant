package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theGamblignant.VriskaMod;
import theGamblignant.actions.PilferAction;
import theGamblignant.characters.TheGamblignant;

import static theGamblignant.VriskaMod.makeCardPath;

public class Pilfer extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(Pilfer.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 2;

    private static final int DAMAGE = 8;


    public Pilfer() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            this.baseDamage = roll(8,'a') + 8;
            this.calculateCardDamage(m);
        }
        this.addToBot(new PilferAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }

    public void applyPowers() {
        super.applyPowers();
        if (this.upgraded) {
            int addeddamage = 8;
            if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
                addeddamage += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            }
            if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
                addeddamage += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
            }
            if (addeddamage >= 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + addeddamage + cardStrings.EXTENDED_DESCRIPTION[2];
            } else if (addeddamage < 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + addeddamage + cardStrings.EXTENDED_DESCRIPTION[2];
            }
        }
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
